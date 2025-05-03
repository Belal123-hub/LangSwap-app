package com.example.data.network.common.interceptors

import com.example.data.network.common.model.TokenRequest
import com.example.data.network.common.model.TokenResponse
import com.example.domain.accessToken.AccessTokenRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun Call.await(recordStack: Boolean = true): Response {
    val callStack = if (recordStack) {
        IOException().apply {
            // Remove unnecessary lines from stacktrace
            // This doesn't remove await$default, but better than nothing
            stackTrace = stackTrace.copyOfRange(1, stackTrace.size)
        }
    } else {
        null
    }
    return suspendCancellableCoroutine { continuation ->
        enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                continuation.resume(response)
            }

            override fun onFailure(call: Call, e: IOException) {
                // Don't bother with resuming the continuation if it is already cancelled.
                if (continuation.isCancelled) return
                callStack?.initCause(e)
                continuation.resumeWithException(callStack ?: e)
            }
        })

        continuation.invokeOnCancellation {
            try {
                cancel()
            } catch (ex: Throwable) {
                //Ignore cancel exception
            }
        }
    }
}

class RefreshTokenAuthenticator(
    private val accessTokenRepository: AccessTokenRepository,
    private val apiUrl: String,
    private val serializer: Json
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking { accessTokenRepository.getRefreshToken() }

        if (token.isNullOrBlank()) return null

        return runBlocking {
            val newToken = getNewToken(TokenRequest(token))

            if (newToken == null) {
                // Couldn't refresh the token, so restart the login process
                accessTokenRepository.setAccessToken(null)
                accessTokenRepository.setRefreshToken(null)
                return@runBlocking null
            }

            accessTokenRepository.setAccessToken(newToken.accessToken)
            accessTokenRepository.setRefreshToken(newToken.refreshToken)

            response.request.newBuilder()
                .header(HeadersInterceptor.HEADER_AUTH, "${HeadersInterceptor.HEADER_BEARER} ${newToken.accessToken}")
                .build()
        }
    }

    private suspend fun getNewToken(refreshToken: TokenRequest): TokenResponse? {
        val okHttpClient = createOkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val request = Request.Builder()
            .url("$apiUrl$REFRESH_PATH")
            .post(serializer.encodeToString(TokenRequest.serializer(), refreshToken).toRequestBody(mediaType))
            .build()
        val response = okHttpClient.newCall(request).await()

        if (!response.isSuccessful) return null

        return try {
            val body = response.body?.string()
            if (body.isNullOrBlank()) null
            else serializer.decodeFromString(TokenResponse.serializer(), body)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun createOkHttpClient(): OkHttpClient { // build new client here to prevent dependency cycle
        val httpClientBuilder = OkHttpClient.Builder().apply {
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            val logLevel = HttpLoggingInterceptor.Level.BODY
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            addInterceptor(
                httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = logLevel
                }
            )
        }
        return httpClientBuilder.build()
    }

    private companion object {
        const val REFRESH_PATH = "v1/auth/refresh"
    }
}
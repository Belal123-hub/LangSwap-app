package com.example.data.network.auth

import com.example.data.network.auth.model.SignInRequest
import com.example.data.network.auth.model.SignUpRequest
import com.example.domain.auth.dataSource.AuthRemoteDataSource
import com.example.domain.auth.dataSource.model.AccessToken

class AuthRemoteDataSourceImpl(
    private val authApi: AuthApi
): AuthRemoteDataSource {

    override suspend fun signUp(
        fullName: String?,
        email: String,
        password: String,
        phoneNumber: String?,
        confirmedPassword: String?
    ): AccessToken {
        val request = SignUpRequest(
            fullName = fullName ?: "",
            email = email,
            password = password,
            confirmPassword = confirmedPassword ?: "",
            phoneNumber = phoneNumber
        )
        val response = authApi.signUp(request)
        return AccessToken(response.accessToken, response.refreshToken, response.userId )
    }

    override suspend fun signIn(email: String, password: String): AccessToken {
        val request = SignInRequest(email = email, password = password)
        val response = authApi.signIn(request)
        return AccessToken(response.accessToken, response.refreshToken, response.userId)
    }

}
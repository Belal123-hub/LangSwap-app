package com.example.data.network.auth

import com.example.data.network.auth.model.AuthRequest
import com.example.domain.auth.dataSource.AuthRemoteDataSource
import com.example.domain.auth.dataSource.model.AccessToken

class AuthRemoteDataSourceImpl(
    private val authApi: AuthApi
): AuthRemoteDataSource {

    override suspend fun signUp(
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String,
        confirmedPassword: String
    ): AccessToken {
        val request = AuthRequest(
            fullName = fullName,
            email = email,
            password = password,
            phoneNumber = phoneNumber,
        )

        val response = authApi.signUp(request)
        return AccessToken(response.accessToken, response.refreshToken)
    }
}
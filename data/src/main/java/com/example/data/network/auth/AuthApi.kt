package com.example.data.network.auth

import com.example.data.network.auth.model.AuthResponse
import com.example.data.network.auth.model.SignInRequest
import com.example.data.network.auth.model.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/user/register")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): AuthResponse

    @POST("api/user/login")
    suspend fun signIn(@Body signInRequest: SignInRequest): AuthResponse
}

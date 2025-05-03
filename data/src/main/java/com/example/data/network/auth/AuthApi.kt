package com.example.data.network.auth

import com.example.data.network.auth.model.AuthRequest
import com.example.data.network.auth.model.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/user/register")
    suspend fun signUp(@Body authRequest: AuthRequest): AuthResponse
}
package com.example.domain.auth.dataSource

import com.example.domain.auth.dataSource.model.AccessToken

interface AuthRemoteDataSource {
    suspend fun signUp(
        fullName: String?,
        email:String,
        password:String,
        phoneNumber: String?,
        confirmedPassword: String?
    ):AccessToken
    suspend fun signIn(email: String,password: String):AccessToken
}


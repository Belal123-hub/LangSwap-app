package com.example.domain.auth

import com.example.domain.auth.dataSource.AuthRemoteDataSource
import com.example.domain.dataStore.DataStoreDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AuthRepository {
    fun isUserSignedIn(): Flow<Boolean>
    suspend fun signUp(fullName: String, email: String, password: String,phoneNumber:String,confirmedPassword:String)
}

class AuthRepositoryImpl(
    private val dataStoreDataSource: DataStoreDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {
    override fun isUserSignedIn() = dataStoreDataSource.accessToken.map { token->
        token != null
    }
    override suspend fun signUp(fullName: String, email: String, password: String,phoneNumber:String,confirmedPassword:String) {
        val token = authRemoteDataSource.signUp(fullName, email, password, phoneNumber,confirmedPassword )
        dataStoreDataSource.setAccessToken(token.accessToken)
        dataStoreDataSource.setRefreshToken(token.refreshToken)
    }
}
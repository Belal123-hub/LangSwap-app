package com.example.domain.auth

import com.example.domain.auth.dataSource.AuthRemoteDataSource
import com.example.domain.auth.usecase.model.AuthInfo
import com.example.domain.dataStore.DataStoreDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AuthRepository {
    fun isUserSignedIn(): Flow<Boolean>
    suspend fun signUp(authInfo: AuthInfo)
    suspend fun signIn(authInfo: AuthInfo)
}

class AuthRepositoryImpl(
    private val dataStoreDataSource: DataStoreDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {
    override fun isUserSignedIn() = dataStoreDataSource.accessToken.map { token->
        token != null
    }
    override suspend fun signUp(authInfo: AuthInfo) {
        val token = authRemoteDataSource.signUp(authInfo.fullName,authInfo.email,authInfo.password,authInfo.confirmPassword,authInfo.phone)
        dataStoreDataSource.setAccessToken(token.accessToken)
        dataStoreDataSource.setRefreshToken(token.refreshToken)
    }

    override suspend fun signIn(authInfo: AuthInfo) {
        val token = authRemoteDataSource.signIn(authInfo.email,authInfo.password)
        dataStoreDataSource.setAccessToken(token.accessToken)
        dataStoreDataSource.setRefreshToken(token.refreshToken)
    }
}
package com.example.domain.auth

import com.example.domain.auth.dataSource.AuthRemoteDataSource
import com.example.domain.auth.usecase.model.SignInInfo
import com.example.domain.auth.usecase.model.SignUpInfo
import com.example.domain.dataStore.DataStoreDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AuthRepository {
    fun isUserSignedIn(): Flow<Boolean>
    suspend fun signUp(signUpInfo: SignUpInfo)
    suspend fun signIn(signInInfo: SignInInfo)
    suspend fun getCurrentUserId(): String?
}

class AuthRepositoryImpl(
    private val dataStoreDataSource: DataStoreDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {
    override fun isUserSignedIn() = dataStoreDataSource.accessToken.map { token->
        token != null
    }
    override suspend fun signUp(signUpInfo: SignUpInfo) {
        val token = authRemoteDataSource.signUp(
            fullName = signUpInfo.fullName,
            email = signUpInfo.email,
            password = signUpInfo.password,
            confirmedPassword = signUpInfo.confirmPassword,
            phoneNumber = signUpInfo.phone
        )
        dataStoreDataSource.setAccessToken(token.accessToken)
        dataStoreDataSource.setRefreshToken(token.refreshToken)
        dataStoreDataSource.setUserId(token.userId)
    }

    override suspend fun signIn(signInInfo: SignInInfo) {
        val token = authRemoteDataSource.signIn(
            email = signInInfo.email,
            password = signInInfo.password
        )
        dataStoreDataSource.setAccessToken(token.accessToken)
        dataStoreDataSource.setRefreshToken(token.refreshToken)
        dataStoreDataSource.setUserId(token.userId)
    }
    override suspend fun getCurrentUserId(): String? {
        return dataStoreDataSource.getUserId() ?: throw Exception("User not authenticated")
    }

}
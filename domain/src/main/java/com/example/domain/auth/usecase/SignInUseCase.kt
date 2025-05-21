package com.example.domain.auth.usecase

import com.example.domain.auth.AuthRepository
import com.example.domain.auth.usecase.model.AuthInfo

interface SignInUseCase {
    suspend operator fun invoke(
        authInfo: AuthInfo
    )
}
class SignInUseCaseImpl(private val authRepository: AuthRepository):SignInUseCase{
    override suspend fun invoke(authInfo: AuthInfo) {
        authRepository.signIn(authInfo)
    }
}
package com.example.domain.auth.usecase

import com.example.domain.auth.AuthRepository
import com.example.domain.auth.usecase.model.AuthInfo


interface SignUpUseCase {
    suspend operator fun invoke(
        authInfo: AuthInfo
    )
}

class SignUpUseCaseImpl(private val authRepository: AuthRepository) : SignUpUseCase {
    override suspend fun invoke(authInfo: AuthInfo) {
        authRepository.signUp(authInfo.fullName, authInfo.email, authInfo.password,authInfo.phone,authInfo.confirmPassword)
    }
}

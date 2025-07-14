package com.example.domain.auth.usecase

import com.example.domain.auth.AuthRepository
import com.example.domain.auth.usecase.model.SignUpInfo


interface SignUpUseCase {
    suspend operator fun invoke(signUpInfo: SignUpInfo)
}

class SignUpUseCaseImpl(
    private val authRepository: AuthRepository
) : SignUpUseCase {
    override suspend fun invoke(signUpInfo: SignUpInfo) {
        authRepository.signUp(signUpInfo)
    }
}

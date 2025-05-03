package com.example.langswap.di

import com.example.domain.auth.usecase.IsUserSignedInUseCase
import com.example.domain.auth.usecase.IsUserSignedInUseCaseImpl
import com.example.domain.auth.usecase.SignUpUseCase
import com.example.domain.auth.usecase.SignUpUseCaseImpl
import com.example.domain.auth.usecase.SignInUseCase
import com.example.domain.auth.usecase.SignInUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::IsUserSignedInUseCaseImpl) { bind<IsUserSignedInUseCase>() }
    factoryOf(::SignUpUseCaseImpl) { bind<SignUpUseCase>() }
    factoryOf(::SignInUseCaseImpl) { bind<SignInUseCase>() }
}
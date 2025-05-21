package com.example.langswap.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.example.langswap.ui.screens.auth.signUp.SignUpViewModel
import com.example.langswap.ui.screens.auth.signIn.SignInViewModel

val viewModelModule  = module {
    viewModelOf(::SignUpViewModel)
    viewModelOf(::SignInViewModel)
}

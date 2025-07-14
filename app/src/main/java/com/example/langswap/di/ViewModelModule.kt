package com.example.langswap.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.example.langswap.ui.screens.auth.signUp.SignUpViewModel
import com.example.langswap.ui.screens.auth.signIn.SignInViewModel
import com.example.langswap.ui.screens.match.MatchViewModel
import com.example.langswap.ui.screens.profile.profileEdit.EditProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.langswap.ui.screens.profile.profileInfo.PublicProfileViewModel
import com.example.langswap.ui.screens.chat.chat.ChatViewModel
import com.example.langswap.ui.screens.chat.chatList.ChatListViewModel

val viewModelModule  = module {
    viewModelOf(::SignUpViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::EditProfileViewModel)
    viewModel { MatchViewModel(get()) }
    viewModelOf(::PublicProfileViewModel)
    viewModelOf(::ChatViewModel)
    viewModelOf(::ChatListViewModel)
}

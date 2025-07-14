package com.example.langswap.di

import com.example.domain.auth.usecase.IsUserSignedInUseCase
import com.example.domain.auth.usecase.IsUserSignedInUseCaseImpl
import com.example.domain.auth.usecase.SignUpUseCase
import com.example.domain.auth.usecase.SignUpUseCaseImpl
import com.example.domain.auth.usecase.SignInUseCase
import com.example.domain.auth.usecase.SignInUseCaseImpl
import com.example.domain.match.usecase.GetMatchesUseCase
import com.example.domain.profile.usecase.GetPublicUserProfileUseCase
import com.example.domain.profile.usecase.GetPublicUserProfileUseCaseImpl
import com.example.domain.profile.usecase.GetUserProfileUseCaseImpl
import com.example.domain.profile.usecase.GetUserProfileUseCase
import com.example.domain.profile.usecase.UpdateUserProfileUseCase
import com.example.domain.profile.usecase.UpdateUserProfileUseCaseImpl
import com.example.domain.chat.usecase.GetChatsUseCase
import com.example.domain.chat.usecase.GetChatsUseCaseImpl
import com.example.domain.chat.usecase.GetChatMessagesUseCase
import com.example.domain.chat.usecase.GetChatMessagesUseCaseImpl
import com.example.domain.chat.usecase.CreateChatUseCase
import com.example.domain.chat.usecase.CreateChatUseCaseImpl
import com.example.domain.chat.usecase.SendMessageUseCase
import com.example.domain.chat.usecase.SendMessageUseCaseImpl
import com.example.domain.auth.usecase.GetCurrentUserIdUseCase
import com.example.domain.auth.usecase.GetCurrentUserIdUseCaseImpl
import com.example.domain.chat.usecase.GetChatByIdUseCase
import com.example.domain.chat.usecase.GetChatByIdUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::IsUserSignedInUseCaseImpl) { bind<IsUserSignedInUseCase>() }
    factoryOf(::SignUpUseCaseImpl) { bind<SignUpUseCase>() }
    factoryOf(::SignInUseCaseImpl) { bind<SignInUseCase>() }
    factoryOf(::GetUserProfileUseCaseImpl) { bind<GetUserProfileUseCase>() }
    factoryOf(::UpdateUserProfileUseCaseImpl) { bind<UpdateUserProfileUseCase>() }
    factory { GetMatchesUseCase(get()) }
    factoryOf(::GetPublicUserProfileUseCaseImpl) { bind<GetPublicUserProfileUseCase>() }
    factoryOf(::GetChatsUseCaseImpl) { bind<GetChatsUseCase>() }
    factoryOf(::GetChatMessagesUseCaseImpl) { bind<GetChatMessagesUseCase>() }
    factoryOf(::CreateChatUseCaseImpl) { bind<CreateChatUseCase>() }
    factoryOf(::SendMessageUseCaseImpl) { bind<SendMessageUseCase>() }
    factoryOf(::GetCurrentUserIdUseCaseImpl) { bind<GetCurrentUserIdUseCase>() }
    factoryOf(::GetChatByIdUseCaseImpl) { bind<GetChatByIdUseCase>() }


}
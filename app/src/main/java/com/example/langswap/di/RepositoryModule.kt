package com.example.langswap.di


import com.example.data.network.match.MatchRepositoryImpl
import com.example.domain.accessToken.AccessTokenRepository
import com.example.domain.accessToken.AccessTokenRepositoryImpl
import com.example.domain.auth.AuthRepository
import com.example.domain.auth.AuthRepositoryImpl
import com.example.domain.profile.ProfileRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.core.module.dsl.bind
import com.example.data.network.profile.ProfileRepositoryImpl
import com.example.domain.chat.ChatRepository
import com.example.domain.match.MatchRepository
import com.example.data.network.chat.ChatRepositoryImpl

val repositoryModule = module {
    factoryOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    factoryOf(::ProfileRepositoryImpl) { bind<ProfileRepository>() }
    factoryOf(::AccessTokenRepositoryImpl) { bind<AccessTokenRepository>()}
    factory<MatchRepository> { MatchRepositoryImpl(get()) }
    factoryOf(::ChatRepositoryImpl) { bind<ChatRepository>() }
}
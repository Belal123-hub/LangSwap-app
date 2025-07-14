package com.example.langswap.di

import com.example.data.network.auth.AuthApi
import com.example.data.network.chat.ChatApi
import com.example.data.network.common.Network
import com.example.data.network.match.MatchApi
import com.example.data.network.profile.ProfileApi
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    factory { Network.okHttpCache }
    single { Network.appJson }
    factoryOf(Network::getJsonFactory)
    factoryOf(Network::getLoggingInterceptor)
    factoryOf(Network::getHeadersInterceptor)
    factoryOf(Network::getRefreshTokenAuthenticator)
    factoryOf(Network::getStatusCodeInterceptor)
    singleOf(Network::getHttpClient)
    singleOf(Network::getRetrofit)
    // apis
    single<AuthApi> { Network.getApi(get()) }
    single<ProfileApi> { Network.getApi(get()) }
    single<MatchApi> { Network.getApi(get()) }
    single<ChatApi> { Network.getApi(get()) }
}
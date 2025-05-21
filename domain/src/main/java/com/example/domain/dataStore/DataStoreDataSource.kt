package com.example.domain.dataStore

import kotlinx.coroutines.flow.Flow

interface DataStoreDataSource {
    val accessToken: Flow<String?>
suspend fun setAccessToken(accessToken: String?)
suspend fun getRefreshToken():String?
suspend fun setRefreshToken(refreshToken:String?)

}
package com.example.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.dataStore.DataStoreDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_name")

class DataStoreDataSourceImpl(
    context: Context
) : DataStoreDataSource {

    private val dataStore = context.dataStore
    override val accessToken = dataStore.data.map { preferences -> preferences[ACCESS_TOKEN]?.ifEmpty { null } }
    override suspend fun setAccessToken(accessToken: String?) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken.orEmpty()
        }
    }

    override suspend fun getRefreshToken() = dataStore.data.first()[REFRESH_TOKEN]?.ifEmpty { null }

    override suspend fun setRefreshToken(refreshToken: String?) {
        dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = refreshToken.orEmpty()
        }
    }



    private companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }
}
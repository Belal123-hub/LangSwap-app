package com.example.data.network.profile

import com.example.data.network.profile.mapper.toDomain
import com.example.data.network.profile.mapper.toDto
import com.example.domain.profile.ProfileRemoteDataSource
import com.example.domain.profile.model.UserProfile
import com.example.domain.profile.model.UserPublicProfile

class ProfileRemoteDataSourceImpl(
    private val api: ProfileApi
) : ProfileRemoteDataSource {

    override suspend fun getProfile(): UserProfile {
        val dto = api.getProfileForEdit()
        return dto.toDomain()
    }

    override suspend fun updateProfile(profile: UserProfile) {
        api.updateProfile(profile.toDto())
    }

    override suspend fun getPublicProfile(userId: String): UserPublicProfile {
        val dto = api.getPublicProfile(userId)
        return dto.toDomain()
    }
}


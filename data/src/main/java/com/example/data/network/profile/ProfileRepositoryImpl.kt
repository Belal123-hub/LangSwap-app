package com.example.data.network.profile
import com.example.data.network.profile.mapper.toDto
import com.example.domain.profile.ProfileRemoteDataSource
import com.example.domain.profile.ProfileRepository
import com.example.domain.profile.model.UserProfile
import com.example.domain.profile.model.UserPublicProfile

class ProfileRepositoryImpl(
    private val remoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {

    override suspend fun getProfile(): UserProfile {
        return remoteDataSource.getProfile()
    }

    override suspend fun updateProfile(profile: UserProfile) {
        remoteDataSource.updateProfile(profile)
    }

    override suspend fun getPublicProfile(userId: String): UserPublicProfile {
        val dto = remoteDataSource.getPublicProfile(userId)
        return dto
    }
}
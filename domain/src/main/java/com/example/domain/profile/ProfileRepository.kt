package com.example.domain.profile

import com.example.domain.profile.model.UserProfile
import com.example.domain.profile.model.UserPublicProfile

interface ProfileRepository {
    suspend fun getProfile(): UserProfile
    suspend fun updateProfile(profile: UserProfile)
    suspend fun getPublicProfile(userId: String): UserPublicProfile
}
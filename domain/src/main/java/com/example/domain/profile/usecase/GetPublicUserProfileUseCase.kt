package com.example.domain.profile.usecase

import com.example.domain.profile.ProfileRepository
import com.example.domain.profile.model.UserPublicProfile


interface GetPublicUserProfileUseCase {
    suspend operator fun invoke(userId: String): UserPublicProfile
}

class GetPublicUserProfileUseCaseImpl(
    private val repository: ProfileRepository
) : GetPublicUserProfileUseCase {
    override suspend fun invoke(userId: String): UserPublicProfile {
        return repository.getPublicProfile(userId)
    }
}
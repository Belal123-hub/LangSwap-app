package com.example.domain.profile.usecase

import com.example.domain.profile.ProfileRepository
import com.example.domain.profile.model.UserProfile

interface UpdateUserProfileUseCase {
    suspend operator fun invoke(profile: UserProfile)
}

class UpdateUserProfileUseCaseImpl(
    private val repository: ProfileRepository
) : UpdateUserProfileUseCase {
    override suspend fun invoke(profile: UserProfile) = repository.updateProfile(profile)
}
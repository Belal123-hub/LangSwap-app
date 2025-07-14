package com.example.domain.profile.usecase

import com.example.domain.profile.ProfileRepository
import com.example.domain.profile.model.UserProfile

interface GetUserProfileUseCase {
    suspend operator fun invoke(): UserProfile
}

class GetUserProfileUseCaseImpl(
    private val repository: ProfileRepository
) : GetUserProfileUseCase {
    override suspend fun invoke() = repository.getProfile()
}
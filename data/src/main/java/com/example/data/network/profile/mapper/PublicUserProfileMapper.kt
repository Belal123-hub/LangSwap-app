package com.example.data.network.profile.mapper

import com.example.data.network.profile.model.PublicUserProfileDto
import com.example.domain.profile.model.UserGoal
import com.example.domain.profile.model.UserPublicProfile

fun PublicUserProfileDto.toDomain(): UserPublicProfile {
    return UserPublicProfile(
        fullName = fullName,
        country = country,
        nativeLanguage = nativeLanguage,
        targetLanguage = targetLanguage,
        profilePhotoUrl = profilePhotoUrl,
        bio = bio,
        goals = goals.map { goal ->
            UserGoal(
                id = goal.id,
                name = goal.name
            )
        }
    )
}
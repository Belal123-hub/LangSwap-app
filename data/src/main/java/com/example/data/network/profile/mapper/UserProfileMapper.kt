package com.example.data.network.profile.mapper

import com.example.data.network.profile.model.GoalSelectionDto
import com.example.data.network.profile.model.UserProfileDto
import com.example.domain.profile.model.Goal
import com.example.domain.profile.model.UserProfile

fun UserProfileDto.toDomain(): UserProfile = UserProfile(
    fullName, birthDate, gender, country, nativeLanguage, targetLanguage,
    profilePhotoUrl, bio, goals.map { Goal(it.id, it.name, it.isSelected) }
)

fun UserProfile.toDto(): UserProfileDto = UserProfileDto(
    fullName, birthDate, gender, country, nativeLanguage, targetLanguage,
    profilePhotoUrl, bio, goals.map { GoalSelectionDto(it.id, it.name, it.isSelected) }
)

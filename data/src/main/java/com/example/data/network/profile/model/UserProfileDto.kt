package com.example.data.network.profile.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
    val fullName: String,
    val birthDate: String?, // ISO-8601 format
    val gender: String,
    val country: String?,
    val nativeLanguage: String,
    val targetLanguage: String,
    val profilePhotoUrl: String?,
    val bio: String?,
    val goals: List<GoalSelectionDto>
)

@Serializable
data class GoalSelectionDto(
    val id: String,
    val name: String,
    val isSelected: Boolean
)


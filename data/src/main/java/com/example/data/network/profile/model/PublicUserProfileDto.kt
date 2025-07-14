package com.example.data.network.profile.model

import kotlinx.serialization.Serializable

@Serializable
data class PublicUserProfileDto(
    val fullName: String,
    val country: String,
    val nativeLanguage: String,
    val targetLanguage: String,
    val profilePhotoUrl: String? = null,
    val bio: String? = null,
    val goals: List<GoalDto>
) {
    @Serializable
    data class GoalDto(
        val id: String,
        val name: String
    )
}
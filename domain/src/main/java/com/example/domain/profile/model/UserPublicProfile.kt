package com.example.domain.profile.model

data class UserPublicProfile(
    val fullName: String,
    val country: String?,
    val nativeLanguage: String,
    val targetLanguage: String,
    val profilePhotoUrl: String?,
    val bio: String?,
    val goals: List<UserGoal> = emptyList()
)

data class UserGoal(
    val id: String,
    val name: String
)

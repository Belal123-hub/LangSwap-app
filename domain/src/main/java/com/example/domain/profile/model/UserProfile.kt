package com.example.domain.profile.model

data class UserProfile(
    val fullName: String,
    val birthDate: String?,
    val gender: String,
    val country: String?,
    val nativeLanguage: String,
    val targetLanguage: String,
    val profilePhotoUrl: String?,
    val bio: String?,
    val goals: List<Goal>
)

data class Goal(
    val id: String,
    val name: String,
    val isSelected: Boolean
)

package com.example.domain.match.model

data class Match(
    val id: String,
    val fullName: String,
    val nativeLanguage: String,
    val targetLanguage: String,
    val profilePhotoUrl: String?,
    val bio: String?
)
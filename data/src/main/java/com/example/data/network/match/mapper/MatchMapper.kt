package com.example.data.network.match.mapper

import com.example.domain.match.model.Match
import kotlinx.serialization.Serializable

@Serializable
data class MatchDto(
    val id: String,
    val fullName: String,
    val nativeLanguage: String,
    val targetLanguage: String,
    val profilePhotoUrl: String? = null,
    val bio: String? = null
)

fun MatchDto.toDomain(): Match {
    return Match(
        id = id,
        fullName = fullName,
        nativeLanguage = nativeLanguage,
        targetLanguage = targetLanguage,
        profilePhotoUrl = profilePhotoUrl,
        bio = bio
    )
}
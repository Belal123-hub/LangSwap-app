package com.example.langswap.ui.screens.profile.profileInfo

import com.example.domain.profile.model.UserPublicProfile

data class PublicProfileUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val profile: UserPublicProfile? = null
)
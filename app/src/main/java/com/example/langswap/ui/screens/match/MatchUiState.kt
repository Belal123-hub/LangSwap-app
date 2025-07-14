package com.example.langswap.ui.screens.match

import com.example.domain.match.model.Match

data class MatchUiState(
    val matches: List<Match> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
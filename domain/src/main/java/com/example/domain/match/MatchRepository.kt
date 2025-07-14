package com.example.domain.match

import com.example.domain.match.model.Match

interface MatchRepository {
    suspend fun getSuggestedMatches(): List<Match>
}
package com.example.domain.match.usecase

import com.example.domain.match.MatchRepository
import com.example.domain.match.model.Match

class GetMatchesUseCase(
    private val matchRepository: MatchRepository
) {
    suspend operator fun invoke(): List<Match> {
        return matchRepository.getSuggestedMatches()
    }
}
package com.example.data.network.match

import com.example.data.network.match.mapper.toDomain
import com.example.domain.match.MatchRepository
import com.example.domain.match.model.Match

class MatchRepositoryImpl(
    private val api: MatchApi
) : MatchRepository {

    override suspend fun getSuggestedMatches(): List<Match> {
        return api.getSuggestedMatches().map { it.toDomain() }
    }
}
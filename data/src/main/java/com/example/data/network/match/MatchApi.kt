package com.example.data.network.match

import com.example.data.network.match.mapper.MatchDto
import retrofit2.http.GET

interface MatchApi {
    @GET("/api/user/matches")
    suspend fun getSuggestedMatches(): List<MatchDto>
}
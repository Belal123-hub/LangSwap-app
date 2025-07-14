package com.example.data.network.profile

import com.example.data.network.profile.model.PublicUserProfileDto
import com.example.data.network.profile.model.UserProfileDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileApi {
    @GET("api/user/profile/edit")
    suspend fun getProfileForEdit(): UserProfileDto

    @PUT("api/user/profile")
    suspend fun updateProfile(@Body profile: UserProfileDto): Response<Unit>

    @GET("api/user/profile/{userId}")
    suspend fun getPublicProfile(@Path("userId") userId: String): PublicUserProfileDto
}

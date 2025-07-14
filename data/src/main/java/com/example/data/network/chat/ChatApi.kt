package com.example.data.network.chat

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatApi {
    @GET("api/chat")
    suspend fun getChats(): List<ChatDto>

    @GET("api/chat/{chatId}")
    suspend fun getChatById(@Path("chatId") chatId: String): ChatDto

    @POST("api/chat/{targetUserId}")
    suspend fun createChat(
        @Path("targetUserId") targetUserId: String
    ): ChatDto

    @GET("api/message/{chatId}")
    suspend fun getMessages(@Path("chatId") chatId: String): List<MessageDto>

    @POST("api/message")
    suspend fun sendMessage(@Body request: CreateMessageRequestDto): MessageDto
}
package com.example.data.network.chat

import com.example.domain.chat.Chat
import com.example.domain.chat.ChatRemoteDataSource
import com.example.domain.chat.CreateMessageRequest
import com.example.domain.chat.Message


class ChatRemoteDataSourceImpl(
    private val api: ChatApi
) : ChatRemoteDataSource {

    override suspend fun getChats(): List<Chat> =
        api.getChats().map { it.toDomain() }

    override suspend fun getChatById(chatId: String): Chat =
        api.getChatById(chatId).toDomain()

    override suspend fun createChat(user1Id: String, user2Id: String): Chat =
        api.createChat(targetUserId = user2Id).toDomain()

    override suspend fun getMessages(chatId: String): List<Message> =
        api.getMessages(chatId).map { it.toDomain() }

    override suspend fun sendMessage(request: CreateMessageRequest): Message =
        api.sendMessage(request.toDto()).toDomain()
}
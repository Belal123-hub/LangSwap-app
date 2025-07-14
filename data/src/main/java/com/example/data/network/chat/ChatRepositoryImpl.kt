package com.example.data.network.chat


import com.example.domain.chat.Chat
import com.example.domain.chat.ChatRepository
import com.example.domain.chat.ChatRemoteDataSource
import com.example.domain.chat.CreateMessageRequest
import com.example.domain.chat.Message
import com.example.domain.chat.Participant

class ChatRepositoryImpl(
    private val remoteDataSource: ChatRemoteDataSource
) : ChatRepository {

    override suspend fun getChats(): List<Chat> =
        remoteDataSource.getChats()

    override suspend fun getChatById(chatId: String): Chat =
        remoteDataSource.getChatById(chatId)

    override suspend fun createChat(user1Id: String, user2Id: String): Chat =
        remoteDataSource.createChat(user1Id, user2Id)

    override suspend fun getMessages(chatId: String): List<Message> =
        remoteDataSource.getMessages(chatId)

    override suspend fun sendMessage(request: CreateMessageRequest): Message =
        remoteDataSource.sendMessage(request)
    }
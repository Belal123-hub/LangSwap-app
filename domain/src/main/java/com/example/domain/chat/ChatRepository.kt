package com.example.domain.chat

interface ChatRepository {
    suspend fun getChats(): List<Chat>
    suspend fun getChatById(chatId: String): Chat
    suspend fun createChat(user1Id: String, user2Id: String): Chat
    suspend fun getMessages(chatId: String): List<Message>
    suspend fun sendMessage(request: CreateMessageRequest): Message
}
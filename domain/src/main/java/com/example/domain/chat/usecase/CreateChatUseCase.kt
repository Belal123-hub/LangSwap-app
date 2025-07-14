package com.example.domain.chat.usecase

import com.example.domain.chat.Chat
import com.example.domain.chat.ChatRepository

interface CreateChatUseCase {
    suspend operator fun invoke(user1Id: String, user2Id: String): Chat
}

class CreateChatUseCaseImpl(
    private val repository: ChatRepository
) : CreateChatUseCase {
    override suspend fun invoke(user1Id: String, user2Id: String): Chat =
        repository.createChat(user1Id, user2Id)
}
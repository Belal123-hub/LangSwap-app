package com.example.domain.chat.usecase

import com.example.domain.chat.Chat
import com.example.domain.chat.ChatRepository

interface GetChatByIdUseCase {
    suspend operator fun invoke(chatId: String): Chat
}

class GetChatByIdUseCaseImpl(
    private val repository: ChatRepository
) : GetChatByIdUseCase {
    override suspend fun invoke(chatId: String): Chat =
        repository.getChatById(chatId)
}

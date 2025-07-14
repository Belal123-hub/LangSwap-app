package com.example.domain.chat.usecase

import com.example.domain.chat.ChatRepository
import com.example.domain.chat.Message

interface GetChatMessagesUseCase {
    suspend operator fun invoke(chatId: String): List<Message>
}

class GetChatMessagesUseCaseImpl(
    private val repository: ChatRepository
) : GetChatMessagesUseCase {
    override suspend fun invoke(chatId: String): List<Message> =
        repository.getMessages(chatId)
}
package com.example.domain.chat.usecase

import com.example.domain.chat.Chat
import com.example.domain.chat.ChatRepository

interface GetChatsUseCase {
    suspend operator fun invoke(): List<Chat>
}

class GetChatsUseCaseImpl(
    private val repository: ChatRepository
) : GetChatsUseCase {
    override suspend fun invoke(): List<Chat> = repository.getChats()
}
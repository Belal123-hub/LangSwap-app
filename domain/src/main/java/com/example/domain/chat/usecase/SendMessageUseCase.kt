package com.example.domain.chat.usecase

import com.example.domain.chat.ChatRepository
import com.example.domain.chat.CreateMessageRequest
import com.example.domain.chat.Message

interface SendMessageUseCase {
    suspend operator fun invoke(request: CreateMessageRequest): Message
}

class SendMessageUseCaseImpl(
    private val repository: ChatRepository
) : SendMessageUseCase {
    override suspend fun invoke(request: CreateMessageRequest): Message =
        repository.sendMessage(request)
}
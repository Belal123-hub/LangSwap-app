package com.example.data.network.chat

import com.example.domain.chat.Chat
import com.example.domain.chat.CreateMessageRequest
import com.example.domain.chat.Message
import com.example.domain.chat.Participant

fun ChatDto.toDomain(): Chat = Chat(
    id = id,
    lastMessage = lastMessage,
    lastUpdated = lastUpdated,
    participants = participants.map { it.toDomain() }
)

fun ParticipantDto.toDomain(): Participant = Participant(
    userId = userId,
    fullName = fullName,
    profilePhotoUrl = profilePhotoUrl
)

// Message Mapper
fun MessageDto.toDomain(): Message = Message(
    id = id,
    chatId = chatId,
    senderId = senderId,
    text = text,
    sentAt = sentAt,
    isRead = isRead
)

fun CreateMessageRequest.toDto(): CreateMessageRequestDto = CreateMessageRequestDto(
    chatId = chatId,
    senderId = senderId,
    text = text
)


package com.example.data.network.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatDto(
    val id: String,
    val lastMessage: String?,
    val lastUpdated: String,
    val participants: List<ParticipantDto>
)
@Serializable
data class ParticipantDto(
    val userId: String,
    val fullName: String,
    val profilePhotoUrl: String?
)
@Serializable
data class MessageDto(
    val id: String,
    val chatId: String,
    val senderId: String,
    val text: String,
    val sentAt: String,
    val isRead: Boolean
)
@Serializable
data class CreateMessageRequestDto(
    val chatId: String,
    val senderId: String,
    val text: String
)
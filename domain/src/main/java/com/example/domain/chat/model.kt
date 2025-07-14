package com.example.domain.chat

data class Chat(
    val id: String,
    val lastMessage: String?,
    val lastUpdated: String,
    val participants: List<Participant>
)

data class Participant(
    val userId: String,
    val fullName: String,
    val profilePhotoUrl: String?
)

data class Message(
    val id: String,
    val chatId: String,
    val senderId: String,
    val text: String,
    val sentAt: String,
    val isRead: Boolean
)

data class CreateMessageRequest(
    val chatId: String,
    val senderId: String,
    val text: String
)
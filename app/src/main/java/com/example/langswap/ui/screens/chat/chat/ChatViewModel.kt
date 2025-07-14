package com.example.langswap.ui.screens.chat.chat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.auth.usecase.GetCurrentUserIdUseCase
import com.example.domain.chat.Chat
import com.example.domain.chat.CreateMessageRequest
import com.example.domain.chat.Message
import com.example.domain.chat.Participant
import com.example.domain.chat.usecase.CreateChatUseCase
import com.example.domain.chat.usecase.GetChatByIdUseCase
import com.example.domain.chat.usecase.GetChatMessagesUseCase
import com.example.domain.chat.usecase.SendMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val createChatUseCase: CreateChatUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getChatByIdUseCase: GetChatByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ChatUiState>(ChatUiState.Loading)
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _chatCreationState = MutableStateFlow<ChatCreationState>(ChatCreationState.Idle)
    val chatCreationState: StateFlow<ChatCreationState> = _chatCreationState

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId: StateFlow<String?> = _currentUserId.asStateFlow()

    private val _participants = MutableStateFlow<List<Participant>>(emptyList())
    val participants: StateFlow<List<Participant>> = _participants

    private val _chat = MutableStateFlow<Chat?>(null)
    val chat: StateFlow<Chat?> = _chat.asStateFlow()



    fun loadMessages(chatId: String) {
        viewModelScope.launch {
            _uiState.value = ChatUiState.Loading
            try {
                _messages.value = getChatMessagesUseCase(chatId)
                _uiState.value = ChatUiState.Success
            } catch (e: Exception) {
                _uiState.value = ChatUiState.Error(e.message ?: "Failed to load messages")
            }
        }
    }

    fun sendMessage(chatId: String, text: String, senderId: String) {
        viewModelScope.launch {
            try {
                val message = sendMessageUseCase(
                    CreateMessageRequest(
                        chatId = chatId,
                        senderId = senderId,
                        text = text
                    )
                )
                _messages.value = _messages.value + message
            } catch (e: Exception) {
                _uiState.value = ChatUiState.Error("Failed to send message")
            }
        }
    }

    fun createChat(currentUserId: String, targetUserId: String) {
        viewModelScope.launch {
            _chatCreationState.value = ChatCreationState.Loading
            try {
                val chatResponse = createChatUseCase(currentUserId, targetUserId)
                _chatCreationState.value = ChatCreationState.Success(chatResponse.id) // Just pass the ID
            } catch (e: Exception) {
                _chatCreationState.value = ChatCreationState.Error(e.message ?: "Failed to start chat")
            }
        }
    }
    fun resetChatCreationState() {
        _chatCreationState.value = ChatCreationState.Idle
    }
    init {
        loadCurrentUserId()
    }

    private fun loadCurrentUserId() {
        viewModelScope.launch {
            try {
                _currentUserId.value = getCurrentUserIdUseCase()
            } catch (e: Exception) {
                _currentUserId.value = null
            }
        }
    }

    fun loadChat(chatId: String) {
        viewModelScope.launch {
            _uiState.value = ChatUiState.Loading
            try {
                val chat = getChatByIdUseCase(chatId)
                _chat.value = chat
                _participants.value = chat.participants
                _uiState.value = ChatUiState.Success
            } catch (e: Exception) {
                _uiState.value = ChatUiState.Error(e.message ?: "Failed to load chat")
            }
        }
    }


}


sealed class ChatUiState {
    object Loading : ChatUiState()
    object Success : ChatUiState()
    data class Error(val message: String) : ChatUiState()
}
sealed class ChatCreationState {
    object Idle : ChatCreationState()
    object Loading : ChatCreationState()
    data class Success(val chatId: String) : ChatCreationState()
    data class Error(val message: String) : ChatCreationState()
}

package com.example.langswap.ui.screens.profile.profileInfo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.auth.usecase.GetCurrentUserIdUseCase
import com.example.domain.profile.usecase.GetPublicUserProfileUseCase
import com.example.langswap.ui.screens.chat.chat.ChatViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PublicProfileViewModel(
    private val getPublicUserProfileUseCase: GetPublicUserProfileUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PublicProfileUiState())
    val uiState: StateFlow<PublicProfileUiState> = _uiState.asStateFlow()

    fun loadPublicProfile(userId: String) {
        _uiState.value = PublicProfileUiState(isLoading = true)

        viewModelScope.launch {
            try {
                val profile = getPublicUserProfileUseCase(userId)
                _uiState.value = PublicProfileUiState(profile = profile)
            } catch (e: Exception) {
                _uiState.value = PublicProfileUiState(error = e.message ?: "Unknown error")
            }
        }
    }
    fun startChatIfPossible(context: Context, chatViewModel: ChatViewModel, targetUserId: String) {
        viewModelScope.launch {
            try {
                val currentUserId = getCurrentUserIdUseCase()
                println("DEBUG: Current User ID: $currentUserId")
                println("DEBUG: Target User ID: $targetUserId")

                if (currentUserId == targetUserId) {
                    Toast.makeText(context, "Cannot chat with yourself", Toast.LENGTH_SHORT).show()
                } else {
                    println("DEBUG: Creating chat between $currentUserId and $targetUserId")
                    chatViewModel.createChat(currentUserId, targetUserId)
                }
            } catch (e: Exception) {
                println("DEBUG: Error starting chat: ${e.message}")
                Toast.makeText(
                    context,
                    "Failed to start chat: ${e.message ?: "Please sign in first"}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
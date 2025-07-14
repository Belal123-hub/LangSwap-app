package com.example.langswap.ui.screens.profile.profileEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.profile.model.UserProfile
import com.example.domain.profile.usecase.GetUserProfileUseCase
import com.example.domain.profile.usecase.UpdateUserProfileUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : ViewModel() {

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile = _profile.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    private val _success = MutableSharedFlow<Unit>()
    val success = _success.asSharedFlow()

    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _profile.value = getUserProfileUseCase()
            } catch (e: Exception) {
                _error.emit("Failed to load profile")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfile(updatedProfile: UserProfile) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                updateUserProfileUseCase(updatedProfile)
                _success.emit(Unit)
            } catch (e: Exception) {
                _error.emit("Failed to update profile")
            } finally {
                _isLoading.value = false
            }
        }
    }
}

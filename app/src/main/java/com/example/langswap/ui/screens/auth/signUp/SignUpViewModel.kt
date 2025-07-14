package com.example.langswap.ui.screens.auth.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.auth.usecase.SignUpUseCase
import com.example.domain.auth.usecase.model.SignUpInfo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _navigateToProfile = MutableSharedFlow<Unit>()
    val navigateToProfile = _navigateToProfile.asSharedFlow()

    private val _showError = MutableSharedFlow<String>()
    val showError = _showError.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun signUp(
        fullName: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (password != confirmPassword) {
                    throw IllegalArgumentException("Passwords don't match")
                }

                signUpUseCase(
                    SignUpInfo(
                        fullName = fullName,
                        email = email,
                        phone = phone,
                        password = password,
                        confirmPassword = confirmPassword
                    )
                ).also {
                    _navigateToProfile.emit(Unit)
                }
            } catch (e: Exception) {
                _showError.emit(e.message ?: "Registration failed")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
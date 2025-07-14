package com.example.langswap.ui.screens.match

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.match.usecase.GetMatchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MatchViewModel(
    private val getMatchesUseCase: GetMatchesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatchUiState())
    val uiState: StateFlow<MatchUiState> = _uiState

    companion object {
        private const val TAG = "MatchViewModel"
    }

    init {
        Log.d(TAG, "ViewModel initialized")
        getMatches()
    }

    private fun getMatches() {
        viewModelScope.launch {
            Log.d(TAG, "Fetching matches...")
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val matches = getMatchesUseCase()
                Log.d(TAG, "Matches fetched successfully: ${matches.size} items")
                _uiState.value = MatchUiState(matches = matches)
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching matches", e)
                _uiState.value = MatchUiState(error = e.message)
            }
        }
    }
}

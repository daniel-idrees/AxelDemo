package com.example.ui.search.state

import com.example.domain.model.User

sealed class SearchUiState {
    object Initial : SearchUiState()
    data class Success(val searchQuery: String, val users: List<User>) : SearchUiState()
    object Error : SearchUiState()
    object Loading : SearchUiState()
}

package com.example.ui.search.state

import com.example.domain.model.User

sealed class SearchResultState {
    object Initial : SearchResultState()
    data class Success(val searchQuery: String, val users: List<User>) : SearchResultState()
    data class Error(val errorMessage: String) : SearchResultState()
    object Loading : SearchResultState()
}

package com.example.ui.search.state

import com.example.domain.model.User

sealed class SearchResultState {
    object Initial : SearchResultState()
    data class Success(val searchQuery: String, val users: List<User>) : SearchResultState()
    object Error : SearchResultState()
    object Loading : SearchResultState()
}

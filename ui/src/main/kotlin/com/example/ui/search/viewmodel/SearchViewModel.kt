package com.example.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.SearchResult
import com.example.domain.model.UserDataResult
import com.example.domain.model.UserDetail
import com.example.domain.usecase.GetUserDetailsUseCase
import com.example.domain.usecase.GetUsersUseCase
import com.example.ui.detail.state.UserDetailState
import com.example.ui.search.state.SearchResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
) : ViewModel() {

    private val _searchResultState =
        MutableStateFlow<SearchResultState>(SearchResultState.Initial)
    val searchResultState: StateFlow<SearchResultState>
        get() = _searchResultState

    private val _userDetailState =
        MutableStateFlow<UserDetailState>(UserDetailState.Loading)
    val userDetailState: StateFlow<UserDetailState>
        get() = _userDetailState

    fun loadDataIfNewSearchQuery(searchQuery: String) {
        if (searchQuery != (searchResultState.value as? SearchResultState.Success)?.searchQuery) {
            loadSearchResult(searchQuery)
        }
    }

    private fun loadSearchResult(searchQuery: String) {
        viewModelScope.launch {
            _searchResultState.emit(SearchResultState.Loading)
            val result = getUsersUseCase.get(searchQuery)
            updateSearchResultState(searchQuery, result)
        }
    }

    private suspend fun updateSearchResultState(
        searchQuery: String,
        result: UserDataResult<SearchResult>,
    ) {
        val viewState = when (result) {
            is UserDataResult.Success -> SearchResultState.Success(searchQuery, result.data.users)
            is UserDataResult.Error -> SearchResultState.Error
        }

        _searchResultState.emit(viewState)
    }

    fun loadDetails(userName: String) {
        viewModelScope.launch {
            _userDetailState.emit(UserDetailState.Loading)
            val result = getUserDetailsUseCase.get(userName)
            updateDetailState(result)
        }
    }

    private suspend fun updateDetailState(result: UserDataResult<UserDetail>) {
        val viewState = when (result) {
            is UserDataResult.Success -> UserDetailState.Success(result.data)
            is UserDataResult.Error -> UserDetailState.Error
        }

        _userDetailState.emit(viewState)
    }
}

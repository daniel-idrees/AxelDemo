package com.example.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.SearchResult
import com.example.domain.model.UserDataResult
import com.example.domain.usecase.GetUsersUseCase
import com.example.ui.search.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
) : ViewModel() {

    private val _searchUiState =
        MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val searchUiState: StateFlow<SearchUiState>
        get() = _searchUiState

    fun loadDataIfNewSearchQuery(searchQuery: String) {
        if (searchQuery != (searchUiState.value as? SearchUiState.Success)?.searchQuery) {
            loadData(searchQuery)
        }
    }

    private fun loadData(searchQuery: String) {
        viewModelScope.launch {
            _searchUiState.emit(SearchUiState.Loading)
            val result = getUsersUseCase.get(searchQuery)
            updateUiState(searchQuery, result)
        }
    }

    private suspend fun updateUiState(searchQuery: String, result: UserDataResult<SearchResult>) {
        val viewState = when (result) {
            is UserDataResult.Success -> SearchUiState.Success(searchQuery, result.data.users)
            is UserDataResult.Error -> SearchUiState.Error
        }

        _searchUiState.emit(viewState)
    }
}

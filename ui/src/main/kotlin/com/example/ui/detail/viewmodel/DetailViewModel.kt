package com.example.ui.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.UserDataResult
import com.example.domain.model.UserDetail
import com.example.domain.usecase.GetUserDetailsUseCase
import com.example.ui.detail.state.DetailUiState
import com.example.ui.nav.detailScreenArgumentUserNameKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _detailUiState =
        MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState>
        get() = _detailUiState

    private val userName = savedStateHandle.get<String>(detailScreenArgumentUserNameKey) ?: ""

    init {
        loadData()
    }

    fun loadData() {
        if (userName.isNotEmpty()) {
            getDetails()
        } else {
            _detailUiState.value = DetailUiState.Error
        }
    }

    private fun getDetails() {
        viewModelScope.launch {
            _detailUiState.emit(DetailUiState.Loading)
            val result = getUserDetailsUseCase.get(userName)
            updateUiState(result)
        }
    }

    private suspend fun updateUiState(result: UserDataResult<UserDetail>) {
        val viewState = when (result) {
            is UserDataResult.Success -> DetailUiState.Success(result.data)
            is UserDataResult.Error -> DetailUiState.Error
        }

        _detailUiState.emit(viewState)
    }
}

package com.example.ui.detail.state

import com.example.domain.model.UserDetail

sealed class DetailUiState {
    data class Success(val detail: UserDetail) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

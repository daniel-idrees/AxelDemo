package com.example.ui.detail.state

import com.example.domain.model.UserDetail

sealed class UserDetailState {
    data class Success(val detail: UserDetail) : UserDetailState()
    object Error : UserDetailState()
    object Loading : UserDetailState()
}

package com.example.ui.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.UserDataResult
import com.example.domain.model.UserDetail
import com.example.domain.usecase.GetUserDetailsUseCase
import com.example.ui.detail.state.UserDetailState
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
    private val _User_detailState =
        MutableStateFlow<UserDetailState>(UserDetailState.Loading)
    val userDetailState: StateFlow<UserDetailState>
        get() = _User_detailState

    private val userName = savedStateHandle.get<String>(detailScreenArgumentUserNameKey) ?: ""

    init {
        loadData()
    }

    fun loadData() {
        if (userName.isNotEmpty()) {
            getDetails()
        } else {
            _User_detailState.value = UserDetailState.Error
        }
    }

    private fun getDetails() {
        viewModelScope.launch {
            _User_detailState.emit(UserDetailState.Loading)
            val result = getUserDetailsUseCase.get(userName)
            updateDetailState(result)
        }
    }

    private suspend fun updateDetailState(result: UserDataResult<UserDetail>) {
        val viewState = when (result) {
            is UserDataResult.Success -> UserDetailState.Success(result.data)
            is UserDataResult.Error -> UserDetailState.Error
        }

        _User_detailState.emit(viewState)
    }
}

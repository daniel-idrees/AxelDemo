package com.example.domain.model

import java.io.IOException

sealed class UserDataResult<T> {
    data class Success<T>(val data: T) : UserDataResult<T>()
    data class Error<T>(val errorMessage: String) : UserDataResult<T>()
}

fun <T> getErrorResult(throwable: Throwable): UserDataResult.Error<T> {
    return when (throwable) {
        is IOException -> UserDataResult.Error("Please check your connection")
        else -> UserDataResult.Error("Something went wrong")
    }
}

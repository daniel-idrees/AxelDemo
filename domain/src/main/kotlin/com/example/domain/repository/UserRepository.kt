package com.example.domain.repository

import com.example.domain.model.SearchResult
import com.example.domain.model.UserDataResult
import com.example.domain.model.UserDetail

interface UserRepository {
    suspend fun getUsers(query: String): UserDataResult<SearchResult>
    suspend fun getUserDetails(userName: String): UserDataResult<UserDetail>
}

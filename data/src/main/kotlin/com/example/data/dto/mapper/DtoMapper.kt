package com.example.data.dto.mapper

import com.example.data.dto.SearchResultResponse
import com.example.domain.model.SearchResult
import com.example.domain.model.User

internal fun SearchResultResponse.toUserList(): SearchResult {
    val list = arrayListOf<User>()
    items.forEach { item ->
        val user = User(
            name = item.name,
            imageUrl = item.avatarURL,
        )
        list.add(user)
    }

    return SearchResult(users = list)
}

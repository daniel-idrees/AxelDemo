package com.example.domain.utils

import com.example.domain.model.SearchResult
import com.example.domain.model.User
import com.example.domain.model.UserDetail

internal object FakeObjects {
    const val mockSearchQuery = "mockSearchQuery"
    const val mockUserName = "mockUserName"
    const val mockImageUrl = "https://www.imageUrl.com"

    val mockSearchResult = SearchResult(
        users = listOf(
            User(
                name = mockUserName,
                imageUrl = mockImageUrl,
            ),
        ),
    )

    val mockUserDetail = UserDetail(mockUserName, 0, 0)
}

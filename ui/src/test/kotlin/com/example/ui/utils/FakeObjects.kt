package com.example.ui.utils

import com.example.domain.model.SearchResult
import com.example.domain.model.User
import com.example.domain.model.UserDetail

internal object FakeObjects {
    const val mockSearchQuery = "mockSearchQuery"
    const val mockUserName = "mockUserName"

    val mockSearchResult = SearchResult(
        users = listOf(
            User(
                name = mockUserName,
                imageUrl = "https://www.imageUrl.com",
            ),
        ),
    )

    val mockUserDetail = UserDetail(mockUserName, 0, 0)
}

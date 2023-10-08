package com.example.data.utils

import com.example.data.dto.ItemDto
import com.example.data.dto.SearchResultResponse
import com.example.data.dto.UserType
import com.example.domain.model.SearchResult
import com.example.domain.model.User
import com.example.domain.model.UserDetail

object FakeObjects {
    val mockSearchQuery = "mockSearchQuery"
    val mockUserName = "mockUserName"
    val mockImageUrl = "https://www.imageUrl.com"

    val mockSearchResultResponse = SearchResultResponse(
        totalCount = 111,
        incompleteResults = false,
        items = listOf(
            ItemDto(
                name = mockUserName,
                url = "",
                avatarURL = mockImageUrl,
                gravatarID = "",
                eventsURL = "",
                htmlURL = "",
                followersURL = "",
                followingURL = "",
                gistsURL = "",
                starredURL = "",
                subscriptionsURL = "",
                organizationsURL = "",
                reposURL = "",
                receivedEventsURL = "",
                id = 99,
                nodeID = "",
                score = 0.0,
                userType = UserType.User,
                siteAdmin = false,
            ),
        ),
    )

    val mockSearchResult = SearchResult(
        users = listOf(
            User(
                name = mockUserName,
                imageUrl = mockImageUrl,
            ),
        ),
    )

    val mockUserDetail = UserDetail(mockUserName, 0 , 0)
}

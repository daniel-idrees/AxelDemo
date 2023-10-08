package com.example.data.dto

import com.squareup.moshi.Json

internal data class ItemDto(
    @field:Json(name = "login")
    val name: String,
    val id: Long,

    @field:Json(name = "node_id")
    val nodeID: String,

    @field:Json(name = "avatar_url")
    val avatarURL: String,

    @field:Json(name = "gravatar_id")
    val gravatarID: String,

    val url: String,

    @field:Json(name = "html_url")
    val htmlURL: String,

    @field:Json(name = "followers_url")
    val followersURL: String,

    @field:Json(name = "following_url")
    val followingURL: String,

    @field:Json(name = "gists_url")
    val gistsURL: String,

    @field:Json(name = "starred_url")
    val starredURL: String,

    @field:Json(name = "subscriptions_url")
    val subscriptionsURL: String,

    @field:Json(name = "organizations_url")
    val organizationsURL: String,

    @field:Json(name = "repos_url")
    val reposURL: String,

    @field:Json(name = "events_url")
    val eventsURL: String,

    @field:Json(name = "received_events_url")
    val receivedEventsURL: String,

    @field:Json(name = "type")
    val userType: UserType,

    @field:Json(name = "site_admin")
    val siteAdmin: Boolean,

    val score: Double,
)

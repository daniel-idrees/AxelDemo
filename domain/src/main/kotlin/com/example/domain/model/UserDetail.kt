package com.example.domain.model

data class UserDetail(
    val userName: String,
    val noOfFollowers: Int? = null,
    val noOfRepositories: Int? = null,
)

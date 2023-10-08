package com.example.data.network

import com.example.data.dto.FollowerDto
import com.example.data.dto.RepositoryDto
import com.example.data.dto.SearchResultResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubUserService {
    @GET("search/users")
    suspend fun getUsers(@Query("q") query: String): SearchResultResponse

    @GET("users/{user_name}/followers")
    suspend fun getUserFollowers(@Path("user_name") userName: String): List<FollowerDto>

    @GET("users/{user_name}/repos")
    suspend fun getUserRepositories(@Path("user_name") userName: String): List<RepositoryDto>
}

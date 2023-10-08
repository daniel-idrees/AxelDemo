package com.example.data.network

import com.example.data.dto.FollowerDto
import com.example.data.dto.RepositoryDto
import com.example.data.dto.SearchResultResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface GithubUserService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") searchQuery: String,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
        @Query("per_page") perPage: Int? = null,
        @Query("page") page: Int? = null,
    ): SearchResultResponse

    @GET("users/{user_name}/followers")
    suspend fun getUserFollowers(@Path("user_name") userName: String): List<FollowerDto>

    @GET("users/{user_name}/repos")
    suspend fun getUserRepositories(@Path("user_name") userName: String): List<RepositoryDto>
}

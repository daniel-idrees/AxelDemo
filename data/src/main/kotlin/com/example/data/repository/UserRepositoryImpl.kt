package com.example.data.repository

import com.example.data.dto.mapper.toUserList
import com.example.data.network.GithubUserService
import com.example.domain.model.SearchResult
import com.example.domain.model.UserDataResult
import com.example.domain.model.UserDetail
import com.example.domain.model.getErrorResult
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubUserService: GithubUserService,
) : UserRepository {

    override suspend fun getUsers(query: String): UserDataResult<SearchResult> =
        runCatching {
            val response = githubUserService.getUsers(query).toUserList()
            UserDataResult.Success(response)
        }.getOrElse {
            getErrorResult(it)
        }

    override suspend fun getUserDetails(userName: String): UserDataResult<UserDetail> =

        supervisorScope {
            var followersCount: Int? = null
            var repositoriesCount: Int? = null
            val repositoriesResponse = async {
                kotlin.runCatching {
                    repositoriesCount = githubUserService.getUserRepositories(userName).size
                }.onFailure {
                    // log
                }
            }
            val followersResponse = async {
                kotlin.runCatching {
                    followersCount = githubUserService.getUserFollowers(userName).size
                }.onFailure {
                    // log
                }
            }
            val followersResult = followersResponse.await()
            val repositoriesResult = repositoriesResponse.await()

            if (followersResult.isFailure && repositoriesResult.isFailure) {
                return@supervisorScope UserDataResult.Error<UserDetail>("Something went wrong")
            }

            val userDetail = UserDetail(
                userName = userName,
                noOfFollowers = followersCount,
                noOfRepositories = repositoriesCount,
            )
            UserDataResult.Success(userDetail)
        }
}

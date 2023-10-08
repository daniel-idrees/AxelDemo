package com.example.data.repository

import com.example.data.network.GithubUserService
import com.example.data.utils.FakeObjects
import com.example.data.utils.FakeObjects.mockSearchQuery
import com.example.data.utils.FakeObjects.mockSearchResult
import com.example.data.utils.FakeObjects.mockUserDetail
import com.example.data.utils.FakeObjects.mockUserName
import com.example.domain.model.UserDataResult
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class UserRepositoryImplTest {
    private val githubUserService: GithubUserService = mock()
    private val subject = UserRepositoryImpl(githubUserService)

    @Test
    fun `searchUsers should return success result when api response is successful`() {
        runBlocking {
            whenever(githubUserService.searchUsers(mockSearchQuery)) doReturn FakeObjects.mockSearchResultResponse
            val result = subject.searchUsers(mockSearchQuery)
            verify(githubUserService).searchUsers(mockSearchQuery)
            verifyNoMoreInteractions(githubUserService)
            assert(result is UserDataResult.Success)
            val expected = mockSearchResult
            (result as UserDataResult.Success).data shouldBe expected
        }
    }

    @Test
    fun `searchUsers should return error result when api response throws exception`() {
        runBlocking {
            whenever(githubUserService.searchUsers(mockSearchQuery)) doThrow IllegalArgumentException()
            val result = subject.searchUsers(mockSearchQuery)

            verify(githubUserService).searchUsers(mockSearchQuery)
            verifyNoMoreInteractions(githubUserService)
            assert(result is UserDataResult.Error)
        }
    }

    @Test
    fun `getUserDetails should success result when both apis responses are successful`() {
        runBlocking {
            whenever(githubUserService.getUserRepositories(mockUserName)) doReturn listOf()
            whenever(githubUserService.getUserFollowers(mockUserName)) doReturn listOf()

            val result = subject.getUserDetails(mockUserName)

            verify(githubUserService).getUserRepositories(mockUserName)
            verify(githubUserService).getUserFollowers(mockUserName)

            verifyNoMoreInteractions(githubUserService)

            val expected = mockUserDetail
            assert(result is UserDataResult.Success)
            (result as UserDataResult.Success).data shouldBe expected
        }
    }

    @Test
    fun `getUserDetails should success result with partial data when getUserRepositories response throws exception`() {
        runBlocking {
            whenever(githubUserService.getUserRepositories(mockUserName)) doThrow IllegalArgumentException()
            whenever(githubUserService.getUserFollowers(mockUserName)) doReturn listOf()

            val result = subject.getUserDetails(mockUserName)

            verify(githubUserService).getUserRepositories(mockUserName)
            verify(githubUserService).getUserFollowers(mockUserName)

            verifyNoMoreInteractions(githubUserService)

            val expected = mockUserDetail.copy(noOfRepositories = null)
            assert(result is UserDataResult.Success)
            (result as UserDataResult.Success).data shouldBe expected
        }
    }

    @Test
    fun `getUserDetails should success result with partial data when getUserFollowers response throws exception`() {
        runBlocking {
            whenever(githubUserService.getUserRepositories(mockUserName)) doReturn listOf()
            whenever(githubUserService.getUserFollowers(mockUserName)) doThrow IllegalArgumentException()

            val result = subject.getUserDetails(mockUserName)

            verify(githubUserService).getUserRepositories(mockUserName)
            verify(githubUserService).getUserFollowers(mockUserName)

            verifyNoMoreInteractions(githubUserService)

            val expected = mockUserDetail.copy(noOfFollowers = null)
            assert(result is UserDataResult.Success)
            (result as UserDataResult.Success).data shouldBe expected
        }
    }

    @Test
    fun `getUserDetails should return error result when api response throws exception`() {
        runBlocking {
            whenever(githubUserService.getUserRepositories(mockUserName)) doThrow IllegalArgumentException()
            whenever(githubUserService.getUserFollowers(mockUserName)) doThrow IllegalArgumentException()

            val result = subject.getUserDetails(mockUserName)

            verify(githubUserService).getUserRepositories(mockUserName)
            verify(githubUserService).getUserFollowers(mockUserName)

            verifyNoMoreInteractions(githubUserService)
            assert(result is UserDataResult.Error)
        }
    }
}

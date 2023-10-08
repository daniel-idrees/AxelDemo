package com.example.ui.search.viewmodel

import com.example.domain.model.UserDataResult
import com.example.domain.usecase.GetUserDetailsUseCase
import com.example.domain.usecase.GetUsersUseCase
import com.example.ui.search.state.SearchResultState
import com.example.ui.search.state.UserDetailState
import com.example.ui.utils.FakeObjects.mockSearchQuery
import com.example.ui.utils.FakeObjects.mockSearchResult
import com.example.ui.utils.FakeObjects.mockUserDetail
import com.example.ui.utils.FakeObjects.mockUserName
import com.patronusgroup.presentation.common.MainDispatcherRule
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class SearchViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getUsersUseCase: GetUsersUseCase = mock()
    private val getUserDetailsUseCase: GetUserDetailsUseCase = mock()
    private val subject = SearchViewModel(getUsersUseCase, getUserDetailsUseCase)

    @Test
    fun `loadDataIfNewSearchQuery should update result state to success if the use case returns success result`() {
        runBlocking {
            whenever(getUsersUseCase.get(mockSearchQuery)) doReturn UserDataResult.Success(
                mockSearchResult,
            )
            subject.loadDataIfNewSearchQuery(mockSearchQuery)
            subject.searchResultState.value shouldBe SearchResultState.Success(
                mockSearchQuery,
                mockSearchResult.users,
            )

            `verify that the data should not load if the search query is not new`()
        }
    }

    private suspend fun `verify that the data should not load if the search query is not new`() {
        subject.loadDataIfNewSearchQuery(mockSearchQuery)
        verify(getUsersUseCase, times(1)).get(mockSearchQuery)
    }

    @Test
    fun `loadDataIfNewSearchQuery should update result state to error if the use case returns error`() {
        runBlocking {
            whenever(getUsersUseCase.get(mockSearchQuery)) doReturn UserDataResult.Error("")
            subject.loadDataIfNewSearchQuery(mockSearchQuery)
            subject.searchResultState.value shouldBe SearchResultState.Error
        }
    }

    @Test
    fun `loadDetails should update detail state to success if the use case returns success result`() {
        runBlocking {
            whenever(getUserDetailsUseCase.get(mockUserName)) doReturn UserDataResult.Success(
                mockUserDetail,
            )
            subject.loadDetails(mockUserName)
            subject.userDetailState.value shouldBe UserDetailState.Success(
                mockUserDetail,
            )
        }
    }

    @Test
    fun `loadDetails should update result state to error if the use case returns error`() {
        runBlocking {
            whenever(getUserDetailsUseCase.get(mockUserName)) doReturn UserDataResult.Error("")
            subject.loadDetails(mockUserName)
            subject.userDetailState.value shouldBe UserDetailState.Error
        }
    }
}

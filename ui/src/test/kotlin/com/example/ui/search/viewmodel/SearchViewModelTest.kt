package com.example.ui.search.viewmodel

import com.example.domain.model.UserDataResult
import com.example.domain.usecase.GetUsersUseCase
import com.example.ui.search.state.SearchUiState
import com.example.ui.utils.FakeObjects.mockSearchQuery
import com.example.ui.utils.FakeObjects.mockSearchResult
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
    private val subject = SearchViewModel(getUsersUseCase)

    @Test
    fun `loadDataIfNewSearchQuery should update result state if the use case returns success result`() {
        runBlocking {
            whenever(getUsersUseCase.get(mockSearchQuery)) doReturn UserDataResult.Success(
                mockSearchResult,
            )
            subject.loadDataIfNewSearchQuery(mockSearchQuery)
            subject.searchUiState.value shouldBe SearchUiState.Success(
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
    fun `loadDataIfNewSearchQuery should make the error state if the use case returns error`() {
        runBlocking {
            whenever(getUsersUseCase.get(mockSearchQuery)) doReturn UserDataResult.Error("")
            subject.loadDataIfNewSearchQuery(mockSearchQuery)
            subject.searchUiState.value shouldBe SearchUiState.Error
        }
    }
}

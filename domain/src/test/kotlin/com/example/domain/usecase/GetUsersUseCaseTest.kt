package com.example.domain.usecase

import com.example.domain.model.UserDataResult
import com.example.domain.repository.UserRepository
import com.example.domain.utils.FakeObjects
import com.example.domain.utils.FakeObjects.mockSearchQuery
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class GetUsersUseCaseTest {
    private val userRepository: UserRepository = mock()
    private val subject = GetUsersUseCase(userRepository)

    @Test
    fun `get should return success result if the repository returns success result`() {
        runBlocking {
            whenever(userRepository.getUsers(mockSearchQuery)) doReturn UserDataResult.Success(
                FakeObjects.mockSearchResult,
            )
            val result = subject.get(mockSearchQuery)
            verify(userRepository).getUsers(mockSearchQuery)
            verifyNoMoreInteractions(userRepository)
            result shouldBe UserDataResult.Success(FakeObjects.mockSearchResult)
        }
    }

    @Test
    fun `get should return error result if the repository returns error result`() {
        runBlocking {
            whenever(userRepository.getUsers(mockSearchQuery)) doReturn UserDataResult.Error("")
            val result = subject.get(mockSearchQuery)
            verify(userRepository).getUsers(mockSearchQuery)
            verifyNoMoreInteractions(userRepository)
            result shouldBe UserDataResult.Error("")
        }
    }
}

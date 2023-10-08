package com.example.domain.usecase

import com.example.domain.utils.FakeObjects.mockUserDetail
import com.example.domain.utils.FakeObjects.mockUserName
import com.example.domain.model.UserDataResult
import com.example.domain.repository.UserRepository
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class GetUserDetailsUseCaseTest {
    private val userRepository: UserRepository = mock()
    private val subject = GetUserDetailsUseCase(userRepository)

    @Test
    fun `get should return the success result if the repository returns success result`() {
        runBlocking {
            whenever(userRepository.getUserDetails(mockUserName)) doReturn UserDataResult.Success(
                mockUserDetail,
            )
            val result = subject.get(mockUserName)
            verify(userRepository).getUserDetails(mockUserName)
            verifyNoMoreInteractions(userRepository)
            result shouldBe UserDataResult.Success(mockUserDetail)
        }
    }

    @Test
    fun `get should return error result if the repository returns error result`() {
        runBlocking {
            whenever(userRepository.getUserDetails(mockUserName)) doReturn UserDataResult.Error("")
            val result = subject.get(mockUserName)
            verify(userRepository).getUserDetails(mockUserName)
            verifyNoMoreInteractions(userRepository)
            result shouldBe UserDataResult.Error("")
        }
    }
}

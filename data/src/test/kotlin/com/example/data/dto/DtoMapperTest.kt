package com.example.data.dto

import com.example.data.dto.mapper.toUserList
import com.example.data.utils.FakeObjects.mockSearchResult
import com.example.data.utils.FakeObjects.mockSearchResultResponse
import com.example.domain.model.SearchResult
import com.example.domain.model.User
import io.kotest.matchers.shouldBe
import org.junit.Test

internal class DtoMapperTest {

    @Test
    fun `Dto should be mapped correctly to domain model`() {
        val response = mockSearchResultResponse
        val result = response.toUserList()
        val expected = mockSearchResult
        result shouldBe expected
    }
}

package com.example.data.dto

import com.squareup.moshi.Json

internal data class SearchResultResponse(
    @field:Json(name = "total_count")
    val totalCount: Long,
    @field:Json(name = "incomplete_results")
    val incompleteResults: Boolean,
    val items: List<ItemDto>,
)

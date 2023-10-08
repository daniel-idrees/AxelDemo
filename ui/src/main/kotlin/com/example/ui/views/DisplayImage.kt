package com.example.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

private val imageSize = 48.dp

@Composable
internal fun DisplayImage(imageUrl: String?) {
    val hasError = remember { mutableStateOf(false) }

    if (!hasError.value) {
        UserImage(imageUrl, hasError)
    } else {
        FallbackImagePlaceholder()
    }
}

@Composable
private fun UserImage(url: String?, hasError: MutableState<Boolean>) {
    AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(imageSize)
            .clip(CircleShape),
        onError = {
            hasError.value = true
        },
    )
}

@Composable
private fun FallbackImagePlaceholder() {
    Box(
        modifier = Modifier
            .size(imageSize)
            .background(
                color = Color.Gray,
                shape = CircleShape,
            ),
    )
}

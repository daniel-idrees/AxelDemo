package com.example.ui.search

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.User
import com.example.ui.search.state.SearchUiState
import com.example.ui.search.viewmodel.SearchViewModel
import com.example.ui.views.DisplayImage
import com.example.ui.views.LoadingView
import com.example.ui.views.spaceL
import com.example.ui.views.spaceS

@Composable
fun SearchScreenView(
    viewModel: SearchViewModel,
    navigateToDetail: (String) -> Unit,
) {
    val viewState by viewModel.searchUiState.collectAsStateWithLifecycle()

    val verticalArrangement =
        if (viewState !is SearchUiState.Success) Arrangement.Center else Arrangement.spacedBy(spaceS)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = spaceS, start = spaceS, end = spaceS),
        verticalArrangement = verticalArrangement,
    ) {
        SearchView(onSearchButtonClick = viewModel::loadDataIfNewSearchQuery)

        when (viewState) {
            is SearchUiState.Loading -> LoadingView()
            is SearchUiState.Success -> UserListView(
                users = (viewState as SearchUiState.Success).users,
                navigateToDetail,
            )

            is SearchUiState.Error -> ShowErrorMessage()
            else -> {}
        }
    }
}

@Composable
private fun ShowErrorMessage() {
    Toast.makeText(
        LocalContext.current,
        "Something went wrong",
        Toast.LENGTH_LONG,
    ).show()
}

@Composable
private fun SearchView(
    onSearchButtonClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = spaceL,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SearchInputField(onSearchButtonClick = onSearchButtonClick)
    }
}

@Composable
private fun SearchInputField(onSearchButtonClick: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    var text by rememberSaveable { mutableStateOf("") }

    val onSearchClick = {
        if (text.isNotBlank()) {
            onSearchButtonClick.invoke(text)
            focusManager.clearFocus()
        }
    }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Search a user") },
    )

    Button(
        onClick = onSearchClick,
        shape = CircleShape,
        contentPadding = PaddingValues(1.dp),
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
private fun UserListView(users: List<User>, onItemClick: (String) -> Unit) {
    if (users.isEmpty()) {
        Text(
            text = "No results found",
        )
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(spaceS),
        ) {
            items(users) { user ->
                UserListItem(
                    user,
                ) { onItemClick(user.name) }
            }
        }
    }
}

@Composable
private fun UserListItem(user: User, onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onItemClick),
        verticalArrangement = Arrangement.spacedBy(spaceS),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spaceS),
        ) {
            DisplayImage(user.imageUrl)
            Text(
                text = user.name,
            )
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}

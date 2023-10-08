package com.example.ui.search

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.User
import com.example.domain.model.UserDetail
import com.example.ui.search.state.SearchResultState
import com.example.ui.search.state.UserDetailState
import com.example.ui.search.viewmodel.SearchViewModel
import com.example.ui.views.DisplayImage
import com.example.ui.views.LoadingView
import com.example.ui.views.spaceS
import com.example.ui.views.spaceXS

@Composable
fun SearchScreenView(
    viewModel: SearchViewModel,
) {
    val searchResultState by viewModel.searchResultState.collectAsStateWithLifecycle()
    val detailDataState by viewModel.userDetailState.collectAsStateWithLifecycle()

    val verticalArrangement =
        if (searchResultState is SearchResultState.Success) {
            Arrangement.spacedBy(
                spaceS,
            )
        } else {
            Arrangement.Center
        }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = verticalArrangement,
    ) {
        SearchBar(onSearchButtonClick = viewModel::loadDataIfNewSearchQuery)
        SearchResultContent(
            searchResultState,
            detailDataState,
            viewModel::loadDetails,
        )
    }
}

@Composable
private fun SearchResultContent(
    searchResultState: SearchResultState,
    detailDataState: UserDetailState,
    onResultItemClick: (String) -> Unit,
) {
    when (searchResultState) {
        is SearchResultState.Loading -> LoadingView()
        is SearchResultState.Success -> ResultView(
            detailDataState,
            users = searchResultState.users,
            onItemClick = onResultItemClick,
        )

        is SearchResultState.Error -> ShowErrorMessage()
        else -> {}
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
private fun SearchBar(onSearchButtonClick: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    var text by rememberSaveable { mutableStateOf("") }

    val onSearchClick = {
        if (text.isNotBlank()) {
            onSearchButtonClick.invoke(text)
            focusManager.clearFocus()
        }
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = spaceS, start = spaceS, end = spaceS),
        trailingIcon = { SearchButton(onSearchClick) },
        value = text,
        singleLine = true,
        onValueChange = { text = it },
        label = { Text("Search a user") },
    )
}

@Composable
private fun SearchButton(onSearchClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(spaceXS),
        onClick = onSearchClick,
        shape = CircleShape,
        contentPadding = PaddingValues(1.dp),
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
        )
    }
}

@Composable
private fun ResultView(
    userDetailState: UserDetailState,
    users: List<User>,
    onItemClick: (String) -> Unit,
) {
    if (users.isEmpty()) {
        Text(
            modifier = Modifier.padding(top = spaceXS, start = spaceS, end = spaceS),
            text = "No results found",
        )
    } else {
        ResultList(userDetailState, users, onItemClick)
    }
}

@Composable
private fun ResultList(
    userDetailState: UserDetailState,
    users: List<User>,
    onItemClick: (String) -> Unit,
) {
    var shouldShowDetail by remember { mutableStateOf(false) }
    val onUserItemClick: (String) -> Unit = { s ->
        run {
            shouldShowDetail = true
            onItemClick(s)
        }
    }

    Column {
        UserListView(
            Modifier.weight(3f),
            users,
            onUserItemClick,
        )

        if (shouldShowDetail) {
            DetailContent(userDetailState, Modifier.weight(1f)) { shouldShowDetail = false }
        }
    }
}

@Composable
private fun UserListView(
    modifier: Modifier,
    users: List<User>,
    function: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .padding(top = spaceXS, start = spaceS, end = spaceS),
        verticalArrangement = Arrangement.spacedBy(spaceS),
    ) {
        items(users) { user ->
            UserListItem(
                user,
            ) {
                function(user.name)
            }
        }
    }
}

@Composable
private fun DetailContent(
    userDetailState: UserDetailState,
    modifier: Modifier,
    onDetailHide: () -> Unit,
) {
    when (userDetailState) {
        is UserDetailState.Success ->
            Box(modifier = modifier) {
                UserDetailView(userDetailState.detail, hideDetailAction = onDetailHide)
            }

        is UserDetailState.Loading ->
            Box(modifier = modifier) {
                LoadingView()
            }

        is UserDetailState.Error -> ShowErrorMessage()
    }
}

@Composable
private fun UserDetailView(detail: UserDetail, hideDetailAction: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxSize(),
        border = BorderStroke(1.dp, Color.Black),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
    ) {
        TopEndCloseButton(onCloseButtonClick = hideDetailAction)
        UserDetailContent(detail)
    }
}

@Composable
private fun TopEndCloseButton(onCloseButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spaceXS),
    ) {
        OutlinedButton(
            onClick = onCloseButtonClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(20.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(1.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
            )
        }
    }
}

@Composable
private fun UserDetailContent(detail: UserDetail) {
    Column(
        modifier = Modifier.padding(start = spaceS, end = spaceS),
        verticalArrangement = Arrangement.spacedBy(spaceXS),
    ) {
        Text(
            text = detail.userName,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )

        if (detail.noOfFollowers != null) {
            Text(
                text = "Followers: ${detail.noOfFollowers}",
            )
        }

        if (detail.noOfRepositories != null) {
            Text(text = "Repositories: ${detail.noOfRepositories}")
        }
    }
}

@Composable
private fun UserListItem(user: User, onItemClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onItemClick),
        verticalArrangement = Arrangement.spacedBy(spaceS),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spaceS),
        ) {
            DisplayImage(user.imageUrl)
            Text(text = user.name)
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}

@Preview(showBackground = true)
@Composable
private fun UserListPreview() {
    ResultView(
        userDetailState = UserDetailState.Loading,
        users = listOf(
            User(name = "user one", "fake url"),
            User(name = "user two", "fake url"),
            User(name = "user three", "fake url"),
        ),
        onItemClick = {},
    )
}

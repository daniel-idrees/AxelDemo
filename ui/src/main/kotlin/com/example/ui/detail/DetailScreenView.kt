package com.example.ui.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.UserDetail
import com.example.ui.detail.state.DetailUiState
import com.example.ui.detail.viewmodel.DetailViewModel
import com.example.ui.views.LoadingView
import com.example.ui.views.spaceS
import com.example.ui.views.spaceXS

@Composable
fun DetailScreenView(
    viewModel: DetailViewModel,
    goBack: () -> Unit,
) {
    val viewState by viewModel.detailUiState.collectAsStateWithLifecycle()
    when (viewState) {
        is DetailUiState.Loading -> LoadingView()
        is DetailUiState.Success -> UserDetailView((viewState as DetailUiState.Success).detail)
        else -> ShowErrorMessage(
            doAfterErrorIsShown = goBack,
        )
    }
}

@Composable
private fun ShowErrorMessage(doAfterErrorIsShown: () -> Unit) {
    Toast.makeText(
        LocalContext.current,
        "Something went wrong",
        Toast.LENGTH_LONG,
    ).show()

    doAfterErrorIsShown()
}

@Composable
private fun UserDetailView(detail: UserDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = spaceS, start = spaceS, end = spaceS),
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

@Preview(showBackground = true)
@Composable
private fun UserDetailPreview() {
    UserDetailView(
        detail = UserDetail(
            userName = "username",
            noOfFollowers = 2,
            noOfRepositories = 3,
        ),
    )
}

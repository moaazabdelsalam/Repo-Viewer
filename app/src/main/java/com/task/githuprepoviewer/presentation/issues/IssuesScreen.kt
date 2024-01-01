package com.task.githuprepoviewer.presentation.issues

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.presentation.CircularAvatarImage
import com.task.githuprepoviewer.presentation.ErrorState
import com.task.githuprepoviewer.presentation.LoadingState
import com.task.githuprepoviewer.presentation.fontFamily

@Composable
fun IssuesScreen() {
    val issuesViewModel: IssuesViewModel = hiltViewModel()
    val state = issuesViewModel.repositoryIssuesState.collectAsStateWithLifecycle()
    val uiState = state.value
    when (uiState) {
        is ApiState.Failure -> ErrorState(message = uiState.error)
        ApiState.Loading -> LoadingState()
        is ApiState.Success -> {
            RepoIssuesList(list = uiState.data, fontFamily = fontFamily)
        }
    }
}

@Composable
fun RepoIssuesList(
    list: List<RepositoryIssuesItem>,
    fontFamily: FontFamily
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(
                list,
                key = { it.author + it.issueTitle + it.date }
            ) {
                RepoIssueItem(repoIssueItem = it, fontFamily = fontFamily)
            }
        }
    }
}

@Composable
fun RepoIssueItem(
    repoIssueItem: RepositoryIssuesItem,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AuthorData(
                    authorName = repoIssueItem.author,
                    authorAvatarUrl = repoIssueItem.authorAvatarUrl,
                    fontFamily = fontFamily
                )
                Text(
                    text = repoIssueItem.date.split("T")[0],
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Light
                    )
                )
            }
            Text(
                text = repoIssueItem.issueTitle,
                modifier = Modifier.padding(vertical = 8.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = repoIssueItem.description ?: "No Description found",
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium
                )
            )
            Row {
                Text(
                    text = repoIssueItem.state,
                    color = if (repoIssueItem.state == "open") Color(0xFF25a850) else Color.Red,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Thin
                    )
                )
            }
        }
    }
}

@Composable
fun AuthorData(
    authorName: String,
    authorAvatarUrl: String,
    fontFamily: FontFamily
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CircularAvatarImage(
            avatarUrl = authorAvatarUrl,
            modifier = Modifier
                .padding(end = 4.dp)
                .size(25.dp)
        )
        Text(
            text = authorName,
            modifier = Modifier.padding(horizontal = 4.dp),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Light
            )
        )
    }
}
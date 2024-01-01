package com.task.githuprepoviewer.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task.githuprepoviewer.R
import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.presentation.CircularAvatarImage
import com.task.githuprepoviewer.presentation.ErrorState
import com.task.githuprepoviewer.presentation.LabeledIcon
import com.task.githuprepoviewer.presentation.LoadingState
import com.task.githuprepoviewer.presentation.RoundedCornerText
import com.task.githuprepoviewer.presentation.fontFamily

@Composable
fun DetailsScreen(
    onShowIssuesClick: (String, String) -> Unit
) {
    val detailsViewModel: DetailsViewModel = hiltViewModel()
    val state = detailsViewModel.repositoryDetailsState.collectAsStateWithLifecycle()
    val uiState = state.value
    when (uiState) {
        is ApiState.Failure -> ErrorState(message = uiState.error)
        ApiState.Loading -> LoadingState()
        is ApiState.Success -> RepoDetails(
            repoDetails = uiState.data,
            fontFamily = fontFamily,
            onShowIssuesClick = {
                onShowIssuesClick(uiState.data.ownerName, uiState.data.repoName)
            }
        )
    }
}

@Composable
fun RepoDetails(
    repoDetails: RepositoryDetails,
    fontFamily: FontFamily,
    onShowIssuesClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        OwnerData(
            repoDetails.ownerName,
            repoDetails.ownerType,
            repoDetails.ownerAvatarUrl,
            fontFamily
        )
        HorizontalLine()
        RepoMainData(
            repoName = repoDetails.repoName,
            isPrivate = repoDetails.isPrivate,
            description = repoDetails.repoDescription ?: "-This Repository has no description!-",
            starsCount = repoDetails.stargazersCount,
            watchersCount = repoDetails.watchersCount,
            forksCount = repoDetails.forksCount,
            language = repoDetails.language,
            createdAt = repoDetails.createdAt.split("T")[0],
            updatedAt = repoDetails.updatedAt.split("T")[0],
            fontFamily = fontFamily
        )
        RepoTopics(repoDetails.topics, fontFamily, Modifier.padding(top = 16.dp))
        RepoIssues(repoDetails.openIssuesCount, onShowIssuesClick)
    }
}

@Composable
fun OwnerData(
    ownerName: String,
    ownerType: String,
    ownerAvatarUrl: String,
    fontFamily: FontFamily
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CircularAvatarImage(
            avatarUrl = ownerAvatarUrl,
            modifier = Modifier
                .padding(end = 16.dp)
                .size(50.dp)
        )
        Column() {
            Text(
                text = ownerName,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                text = ownerType,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Thin
                )
            )
        }
    }
}

@Composable
fun RepoMainData(
    repoName: String,
    isPrivate: Boolean,
    description: String,
    starsCount: Int,
    watchersCount: Int,
    forksCount: Int,
    language: String,
    createdAt: String,
    updatedAt: String,
    fontFamily: FontFamily
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = repoName,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Created: $createdAt",
                style = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LabeledIcon(
                painter = painterResource(id = if (isPrivate) R.drawable.ic_private else R.drawable.ic_public),
                label = if (isPrivate) "Private" else "Public",
                contentDescription = "Visibility Icon",
                colorFilter = ColorFilter.tint(Color.Gray),
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(18.dp),
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Thin
                )
            )
            Text(
                text = "Updated: $updatedAt",
                style = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Text(
            text = description,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium
            )
        )
        RepoProperties(
            starsCount = starsCount,
            watchersCount = watchersCount,
            forksCount = forksCount,
            language = language
        )
    }
}

@Composable
fun RepoProperties(
    starsCount: Int,
    watchersCount: Int,
    forksCount: Int,
    language: String,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        LabeledCounterItem(
            painter = painterResource(id = R.drawable.ic_star),
            number = starsCount.toString(),
            label = "Stars",
            contentDescription = "Star Icon",
            colorFilter = ColorFilter.tint(Color.Gray),
            modifier = Modifier
                .padding(end = 8.dp)
                .size(25.dp)
        )
        LabeledCounterItem(
            painter = painterResource(id = R.drawable.ic_fork),
            number = forksCount.toString(),
            label = "Forks",
            contentDescription = "Fork Icon",
            colorFilter = ColorFilter.tint(Color.Gray),
            modifier = Modifier
                .padding(end = 8.dp)
                .size(25.dp)
        )
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        LabeledCounterItem(
            painter = painterResource(id = R.drawable.ic_watcher),
            number = watchersCount.toString(),
            label = "Watchers",
            contentDescription = "Watcher Icon",
            colorFilter = ColorFilter.tint(Color.Gray),
            modifier = Modifier
                .padding(end = 8.dp)
                .size(25.dp)
        )
        LabeledCounterItem(
            painter = painterResource(id = R.drawable.ic_code),
            number = "",
            label = language,
            contentDescription = "Language Icon",
            colorFilter = ColorFilter.tint(Color.Gray),
            modifier = Modifier
                .padding(end = 8.dp)
                .size(25.dp)
        )
    }
}

@Composable
fun LabeledCounterItem(
    painter: Painter,
    number: String,
    label: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter,
    textStyle: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold
    )
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        LabeledIcon(
            painter = painter,
            label = number,
            contentDescription = contentDescription,
            colorFilter = colorFilter,
            modifier = modifier,
            textStyle = textStyle
        )
        Text(
            text = label,
            style = textStyle,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun RepoTopics(
    topics: List<String>,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier
) {
    if (topics.isNotEmpty()) {
        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                text = "Topics",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            LazyRow() {
                items(topics) {
                    RoundedCornerText(
                        text = it,
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Thin
                        ),
                        backgroundColor = Color.LightGray
                    )
                }
            }
        }
    }
}

@Composable
fun RepoIssues(
    issuesCount: Int,
    onShowIssuesClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            enabled = issuesCount > 0,
            onClick = { onShowIssuesClick() }) {
            Text(
                text =
                if (issuesCount > 0)
                    "See $issuesCount issues"
                else
                    "No Issues"
            )
        }
    }
}

@Composable
fun HorizontalLine() {
    Box(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .height(2.dp)
            .background(Color.LightGray)
    )
}

/*
@Preview
@Composable
fun PreviewRepDetails() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        RepoDetails(
            repoDetails = RepositoryDetails(
                ownerName = "vanpelt",
                ownerAvatarUrl = "https://avatars.githubusercontent.com/u/17?v=4",
                ownerType = "User",
                repoName = "jsawesome",
                repoDescription = "Awesome JSON",
                isPrivate = false, stargazersCount = 71,
                watchersCount = 71,
                forksCount = 11,
                language = "JavaScript",
                topics = listOf(
                    "programming-languages",
                    "rubinius",
                    "virtual-machine"
                ),
                openIssuesCount = 1,
                createdAt = "2008-01-13T06:04:19Z",
                updatedAt = "2023-10-25T05:52:54Z"
            ),
            fontFamily = fontFamily
        )
    }
}*/

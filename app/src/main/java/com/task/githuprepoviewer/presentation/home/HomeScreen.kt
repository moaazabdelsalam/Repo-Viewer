package com.task.githuprepoviewer.presentation.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.task.githuprepoviewer.R
import com.task.githuprepoviewer.data.remote.ApiState

@Composable
fun HomeScreen(
    state: ApiState<List<HomeRepositoryItem>>,
    fontFamily: FontFamily,
    onItemClick: (String, String) -> Unit
) {
    val TAG = "TAG HomeScreen"

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        when (state) {
            is ApiState.Failure -> Log.i(TAG, "failure: ${state.error}")
            ApiState.Loading -> CircularProgressIndicator()
            is ApiState.Success -> {
                LazyColumn() {
                    items(
                        state.data,
                        key = { it.ownerName + it.repoName }
                    ) {
                        RepoItem(
                            homeRepositoryItem = it,
                            onItemClick = onItemClick,
                            modifier = Modifier.fillMaxWidth(),
                            fontFamily
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RepoItem(
    homeRepositoryItem: HomeRepositoryItem,
    onItemClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .padding(8.dp)
            .clickable { onItemClick(homeRepositoryItem.ownerName, homeRepositoryItem.repoName) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CircularAvatarImage(
                    avatarUrl = homeRepositoryItem.ownerAvatarUrl,
                    modifier = Modifier.size(25.dp).padding(end = 4.dp)
                )
                Text(
                    text = homeRepositoryItem.ownerName,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Light
                    )
                )
                Text(
                    text = "/",
                    color = Color.Gray,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Italic
                    )
                )
                Text(
                    text = homeRepositoryItem.repoName,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Text(
                text = homeRepositoryItem.repoDescription ?: "",
                modifier = Modifier.padding(vertical = 8.dp),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium
                )
            )
            Row() {
                StargazersCount(
                    stargazersNumber = homeRepositoryItem.starsCount.toString(),
                    fontFamily = fontFamily
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CircularAvatarImage(
    avatarUrl: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        GlideImage(
            model = avatarUrl,
            contentDescription = "Owner Avatar Image",
            loading = placeholder(R.drawable.jetpack_compose_icon),
            failure = placeholder(ColorPainter(Color.Red)),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxSize()
        )
    }
}

@Composable
fun StargazersCount(
    stargazersNumber: String,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            imageVector = Icons.Filled.Star,
            contentDescription = "Star Icon",
            modifier = modifier
                .padding(4.dp),
            colorFilter = ColorFilter.tint(
                Color(0xFFFFD700)
            )
        )
        Text(
            text = stargazersNumber,
            style = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

/*
@Preview
@Composable
fun PreviewRepoItem() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            RepoItem(
                repositoryItem = RepositoryItem(
                    ownerName = "mojombo",
                    ownerAvatarUrl = "",
                    repoName = "grit",
                    repoDescription = "Grit is no longer maintained. Check out libgit2/rugged.** Grit gives you object oriented read/write access to Git repositories via Ruby.",
                    starsCount = 10
                ),
                onItemClick = { },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

}*/

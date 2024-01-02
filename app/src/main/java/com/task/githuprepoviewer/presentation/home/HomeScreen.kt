package com.task.githuprepoviewer.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.presentation.CircularAvatarImage
import com.task.githuprepoviewer.presentation.ErrorState
import com.task.githuprepoviewer.presentation.LoadingState
import com.task.githuprepoviewer.presentation.isReachedBottom

@Composable
fun HomeScreen(
    state: ApiState<List<HomeRepositoryItem>>,
    fontFamily: FontFamily,
    loadMore: () -> Unit,
    onQueryChange: (String) -> Unit,
    filteredList: List<HomeRepositoryItem>,
    onItemClick: (String, String) -> Unit
) {
    when (state) {
        is ApiState.Failure -> ErrorState(state.error)
        ApiState.Loading -> LoadingState()
        is ApiState.Success -> HomeReposList(
            list = state.data,
            loadMore = loadMore,
            onItemClick = onItemClick,
            fontFamily = fontFamily,
            onQueryChange = onQueryChange,
            filteredList = filteredList
        )
    }
}

@Composable
fun HomeReposList(
    list: List<HomeRepositoryItem>,
    loadMore: () -> Unit,
    onItemClick: (String, String) -> Unit,
    fontFamily: FontFamily,
    onQueryChange: (String) -> Unit,
    filteredList: List<HomeRepositoryItem>
) {
    val listState = rememberLazyListState()
    val reachedBottom: Boolean by remember {
        derivedStateOf {
            listState.isReachedBottom()
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) {
            loadMore()
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        MySearchBar(onQueryChange, filteredList, onItemClick, fontFamily)
        LazyColumn(state = listState) {
            items(
                list,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    onQueryChange: (String) -> Unit,
    filteredList: List<HomeRepositoryItem>,
    onItemClick: (String, String) -> Unit,
    fontFamily: FontFamily
) {
    var searchText by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        query = searchText,
        onQueryChange = {
            searchText = it
            onQueryChange(searchText.trim())
        },
        onSearch = {},
        active = isActive,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (isActive) {
                Icon(
                    modifier = Modifier.clickable {
                        if (searchText.isNotBlank()) {
                            searchText = ""
                        } else {
                            isActive = false
                        }
                    },
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Clear Icon"
                )
            }
        },
        placeholder = { Text(text = "Search with Repo Name or Owner") },
        onActiveChange = { isActive = it }
    ) {
        if (searchText.isNotBlank()) {
            LazyColumn() {
                items(filteredList) {
                    RepoHeader(
                        ownerAvatarUrl = it.ownerAvatarUrl,
                        ownerName = it.ownerName,
                        repoName = it.repoName,
                        fontFamily = fontFamily,
                        modifier = Modifier
                            .padding(6.dp)
                            .clickable { onItemClick(it.ownerName, it.repoName) }
                    )
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
            homeRepositoryItem.let { item ->
                RepoHeader(item.ownerAvatarUrl, item.ownerName, item.repoName, fontFamily)
            }
            Text(
                text = homeRepositoryItem.repoDescription
                    ?: "-This Repository has no description!-",
                modifier = Modifier.padding(vertical = 8.dp),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
private fun RepoHeader(
    ownerAvatarUrl: String,
    ownerName: String,
    repoName: String,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        CircularAvatarImage(
            avatarUrl = ownerAvatarUrl,
            modifier = Modifier
                .padding(end = 4.dp)
                .size(25.dp)
        )
        Text(
            text = ownerName,
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
            text = repoName,
            modifier = Modifier.padding(horizontal = 4.dp),
            maxLines = 1,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
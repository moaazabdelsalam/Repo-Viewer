package com.task.githuprepoviewer.presentation.issues

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.presentation.details.LoadingState

@Composable
fun IssuesScreen() {
    val issuesViewModel: IssuesViewModel = hiltViewModel()
    val state = issuesViewModel.repositoryIssuesState.collectAsStateWithLifecycle()
    val uiState = state.value
    when (uiState) {
        is ApiState.Failure -> Log.i("TAG IssuesScreen", "failure: ${uiState.error}")
        ApiState.Loading -> LoadingState()
        is ApiState.Success -> {
            Log.i("TAG IssuesScreen", "IssuesScreen: ${uiState.data}")
        }
    }
}
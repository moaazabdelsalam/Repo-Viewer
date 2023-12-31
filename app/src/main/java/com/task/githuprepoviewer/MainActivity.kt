package com.task.githuprepoviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.task.githuprepoviewer.presentation.details.DetailsScreen
import com.task.githuprepoviewer.presentation.fontFamily
import com.task.githuprepoviewer.presentation.home.HomeScreen
import com.task.githuprepoviewer.presentation.home.HomeViewModel
import com.task.githuprepoviewer.presentation.issues.IssuesScreen
import com.task.githuprepoviewer.ui.theme.GithupRepoViewerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = "TAG MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithupRepoViewerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GitHupRepoViewerApp()
                }
            }
        }
    }

    @Composable
    private fun GitHupRepoViewerApp() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "home") {
            composable(route = "home") {
                val homeViewModel: HomeViewModel = hiltViewModel()
                val uiState = homeViewModel.repositoryListState.collectAsStateWithLifecycle()
                HomeScreen(
                    state = uiState.value,
                    fontFamily = fontFamily,
                    loadMore = { homeViewModel.loadMoreRepos() }
                ) { ownerName, repoName ->
                    navController.navigate("details/$ownerName/$repoName")
                }
            }
            composable(route = "details/{owner}/{repo}",
                arguments = listOf(
                    navArgument("owner") { type = NavType.StringType },
                    navArgument("repo") { type = NavType.StringType }
                )
            ) {
                DetailsScreen { ownerName, repoName ->
                    navController.navigate("issues/$ownerName/$repoName")
                }
            }
            composable(route = "issues/{owner}/{repo}",
                arguments = listOf(
                    navArgument("owner") { type = NavType.StringType },
                    navArgument("repo") { type = NavType.StringType }
                )
            ) {
                IssuesScreen()
            }
        }
    }
}
package com.task.githuprepoviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.task.githuprepoviewer.presentation.home.HomeScreen
import com.task.githuprepoviewer.presentation.home.HomeViewModel
import com.task.githuprepoviewer.ui.theme.GithupRepoViewerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = "TAG MainActivity"
    private val fontFamily = FontFamily(
        Font(R.font.mulish_bold, FontWeight.Bold),
        Font(R.font.mulish_semi_bold_italic, FontWeight.SemiBold),
        Font(R.font.mulish_medium, FontWeight.Medium),
        Font(R.font.mulish_light, FontWeight.Light)
    )

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
                HomeScreen(state = uiState.value, fontFamily = fontFamily)
            }
        }
    }
}
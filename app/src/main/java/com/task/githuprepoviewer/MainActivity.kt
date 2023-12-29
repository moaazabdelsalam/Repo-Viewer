package com.task.githuprepoviewer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.presentation.home.HomeViewModel
import com.task.githuprepoviewer.ui.theme.GithupRepoViewerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = "TAG MainActivity"
    val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithupRepoViewerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }


        lifecycleScope.launch(Dispatchers.IO) {
            homeViewModel.repositoryListState.collectLatest {state ->
                when(state){
                    is ApiState.Failure -> Log.i(TAG, "failure: ${state.error}")
                    is ApiState.Loading -> Log.i(TAG, "loading")
                    is ApiState.Success -> Log.i(TAG, "success: ${state.data}")
                }
            }
        }
    }
}
package com.task.githuprepoviewer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.task.githuprepoviewer.data.local.LocalSourceImp
import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.data.remote.GitHubService
import com.task.githuprepoviewer.data.remote.RemoteSourceImp
import com.task.githuprepoviewer.data.repo.RepoImp
import com.task.githuprepoviewer.ui.theme.GithupRepoViewerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private val TAG = "TAG MainActivity"
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
        val retrofit:Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val repo = RepoImp(
            RemoteSourceImp(retrofit.create(GitHubService::class.java)),
            LocalSourceImp()
        )
        lifecycleScope.launch(Dispatchers.IO) {
            repo.getRepositoryList().collectLatest {state ->
                when(state){
                    is ApiState.Failure -> Log.i(TAG, "failure: ${state.error}")
                    is ApiState.Loading -> Log.i(TAG, "loading")
                    is ApiState.Success -> Log.i(TAG, "success: ${state.data.size}")
                }
            }
        }
    }
}
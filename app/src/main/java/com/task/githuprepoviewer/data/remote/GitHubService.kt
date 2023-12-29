package com.task.githuprepoviewer.data.remote

import com.task.githuprepoviewer.data.remote.model.RepositoryListResponse
import retrofit2.http.GET

interface GitHubService {
    @GET("repositories")
    suspend fun getRepositoryList(): List<RepositoryListResponse>
}
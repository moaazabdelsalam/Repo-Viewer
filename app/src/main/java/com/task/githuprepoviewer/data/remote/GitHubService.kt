package com.task.githuprepoviewer.data.remote

import com.task.githuprepoviewer.data.remote.model.RepositoryResponse
import retrofit2.http.GET

interface GitHubService {
    @GET("repositories")
    suspend fun getRepositoryList(): List<RepositoryResponse>
}
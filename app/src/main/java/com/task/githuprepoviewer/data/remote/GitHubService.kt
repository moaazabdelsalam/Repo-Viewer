package com.task.githuprepoviewer.data.remote

import com.task.githuprepoviewer.data.remote.model.RepositoryDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("repositories")
    suspend fun getRepositoryList(): List<RepositoryDetailsResponse>

    @GET("repos/{owner}/{repo}")
    suspend fun getFullRepositoryDetails(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String
    ): RepositoryDetailsResponse
}
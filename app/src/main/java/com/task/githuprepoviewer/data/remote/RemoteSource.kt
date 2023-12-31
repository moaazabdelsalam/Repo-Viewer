package com.task.githuprepoviewer.data.remote

import com.task.githuprepoviewer.data.remote.model.RepositoryDetailsResponse
import com.task.githuprepoviewer.data.remote.model.RepositoryIssuesResponse
import kotlinx.coroutines.flow.Flow

interface RemoteSource {
    fun getRepositoryList(): Flow<List<RepositoryDetailsResponse>>
    fun getFullRepositoryDetails(ownerName: String, repoName: String): Flow<RepositoryDetailsResponse>
    fun getRepositoryIssues(ownerName: String, repoName: String): Flow<List<RepositoryIssuesResponse>>
}
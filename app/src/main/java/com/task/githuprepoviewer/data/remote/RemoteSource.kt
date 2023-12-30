package com.task.githuprepoviewer.data.remote

import com.task.githuprepoviewer.data.remote.model.RepositoryDetailsResponse
import kotlinx.coroutines.flow.Flow

interface RemoteSource {
    fun getRepositoryList(): Flow<List<RepositoryDetailsResponse>>
    fun getFullRepositoryDetails(ownerName: String, repoName: String): Flow<RepositoryDetailsResponse>
}
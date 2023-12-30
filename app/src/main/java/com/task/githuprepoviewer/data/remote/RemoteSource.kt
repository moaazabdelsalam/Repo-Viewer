package com.task.githuprepoviewer.data.remote

import com.task.githuprepoviewer.data.remote.model.RepositoryDetailsResponse
import com.task.githuprepoviewer.data.remote.model.RepositoryResponse
import kotlinx.coroutines.flow.Flow

interface RemoteSource {
    fun getRepositoryList(): Flow<List<RepositoryResponse>>
    fun getFullRepositoryDetails(ownerName: String, repoName: String): Flow<RepositoryDetailsResponse>
}
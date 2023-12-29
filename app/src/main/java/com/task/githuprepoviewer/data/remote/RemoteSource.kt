package com.task.githuprepoviewer.data.remote

import com.task.githuprepoviewer.data.remote.model.RepositoryResponse
import kotlinx.coroutines.flow.Flow

interface RemoteSource {
    suspend fun getRepositoryList(): Flow<List<RepositoryResponse>>
}
package com.task.githuprepoviewer.data.remote

import com.task.githuprepoviewer.data.remote.model.RepositoryResponse

interface RemoteSource {
    suspend fun getRepositoryList(): List<RepositoryResponse>
}
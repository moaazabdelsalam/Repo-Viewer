package com.task.githuprepoviewer.data.remote

import com.task.githuprepoviewer.data.remote.model.RepositoryListResponse

interface RemoteSource {
    suspend fun getRepositoryList(): List<RepositoryListResponse>
}
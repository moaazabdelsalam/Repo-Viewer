package com.task.githuprepoviewer.data.repo

import com.task.githuprepoviewer.data.remote.model.RepositoryDetailsResponse
import com.task.githuprepoviewer.presentation.RepositoryItem
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun updateLocalRepositoryList()
    fun getRepositoryList(): Flow<List<RepositoryItem>>
    fun getFullRepositoryDetails(ownerName: String, repoName: String): Flow<RepositoryDetailsResponse>
}
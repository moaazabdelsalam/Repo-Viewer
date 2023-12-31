package com.task.githuprepoviewer.data.repo

import com.task.githuprepoviewer.presentation.details.RepositoryDetails
import com.task.githuprepoviewer.presentation.home.HomeRepositoryItem
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun updateLocalRepositoryList()
    fun getRepositoryList(): Flow<List<HomeRepositoryItem>>
    fun getFullRepositoryDetails(ownerName: String, repoName: String): Flow<RepositoryDetails>
}
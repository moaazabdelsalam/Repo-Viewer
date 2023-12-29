package com.task.githuprepoviewer.data.repo

import com.task.githuprepoviewer.presentation.RepositoryItem
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun updateLocalRepositoryList()
    fun getRepositoryList(): Flow<List<RepositoryItem>>
}
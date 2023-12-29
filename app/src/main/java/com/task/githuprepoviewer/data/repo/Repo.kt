package com.task.githuprepoviewer.data.repo

import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.presentation.RepositoryItem
import kotlinx.coroutines.flow.Flow

interface Repo {
    fun getRepositoryList(): Flow<ApiState<List<RepositoryItem>>>
}
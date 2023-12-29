package com.task.githuprepoviewer.data.repo

import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.data.remote.model.RepositoryListResponse
import kotlinx.coroutines.flow.Flow

interface Repo {
    fun getRepositoryList(): Flow<ApiState<List<RepositoryListResponse>>>
}
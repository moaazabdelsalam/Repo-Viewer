package com.task.githuprepoviewer.data.repo

import com.task.githuprepoviewer.data.local.LocalSource
import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.data.remote.RemoteSource
import com.task.githuprepoviewer.data.remote.model.RepositoryListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RepoImp(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource
) : Repo {
    override fun getRepositoryList(): Flow<ApiState<List<RepositoryListResponse>>> {
        return flow {
            emit(ApiState.Loading)
            emit(ApiState.Success(remoteSource.getRepositoryList()))
        }.catch {
            emit(ApiState.Failure("exception: ${it.message}"))
        }
    }
}
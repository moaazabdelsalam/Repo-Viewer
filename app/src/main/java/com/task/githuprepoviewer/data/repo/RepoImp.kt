package com.task.githuprepoviewer.data.repo

import com.task.githuprepoviewer.data.local.LocalSource
import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.data.remote.RemoteSource
import com.task.githuprepoviewer.presentation.RepositoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoImp @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource
) : Repo {

    override fun getRepositoryList(): Flow<ApiState<List<RepositoryItem>>> {
        return flow {
            emit(ApiState.Loading)
            val list = remoteSource.getRepositoryList().map {
                it.convertToRepositoryItem()
            }
            emit(ApiState.Success(list))
        }.catch {
            emit(ApiState.Failure("exception: ${it.message}"))
        }
    }
}
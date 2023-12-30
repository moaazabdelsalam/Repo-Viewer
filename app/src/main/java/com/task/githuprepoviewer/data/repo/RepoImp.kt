package com.task.githuprepoviewer.data.repo

import android.util.Log
import com.task.githuprepoviewer.data.local.LocalSource
import com.task.githuprepoviewer.data.remote.RemoteSource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoImp @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource
) : Repo {
    private val TAG = "TAG RepoImp"
    override fun getRepositoryList() = flow {
        emit(localSource.getLocalRepositoryList().convertToRepositoryItemsList())
    }

    override suspend fun updateLocalRepositoryList() {
        remoteSource.getRepositoryList()
            .catch {
                Log.i(TAG, "updateLocalRepositoryList exception: ${it.message}")
                if (localSource.getLocalRepositoryList().isEmpty()) {
                    throw Exception("Something went wrong. No data was found, try connecting to internet.")
                }
            }.collectLatest { repositoryResponse ->
                localSource.updateLocalRepositoryList(repositoryResponse.convertToLocalRepositoryItemsList())
            }
    }

    override fun getFullRepositoryDetails(
        ownerName: String,
        repoName: String
    ) = remoteSource.getFullRepositoryDetails(ownerName, repoName)

}
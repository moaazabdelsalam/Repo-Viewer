package com.task.githuprepoviewer.data.remote

import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteSourceImp @Inject constructor(
    private val service: GitHubService
) : RemoteSource {

    override fun getRepositoryList() = flow {
        emit(service.getRepositoryList())
    }

    override fun getFullRepositoryDetails(ownerName: String, repoName: String) = flow {
        emit(service.getFullRepositoryDetails(ownerName, repoName))
    }
}
package com.task.githuprepoviewer.data.remote

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteSourceImp @Inject constructor(
    private val service: GitHubService
) : RemoteSource {
    override suspend fun getRepositoryList() = service.getRepositoryList()
}
package com.task.githuprepoviewer.data.remote

class RemoteSourceImp(
    private val service: GitHubService
) : RemoteSource {
    override suspend fun getRepositoryList() = service.getRepositoryList()
}
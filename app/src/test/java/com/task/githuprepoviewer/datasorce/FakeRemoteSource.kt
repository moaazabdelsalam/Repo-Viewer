package com.task.githuprepoviewer.datasorce

import com.task.githuprepoviewer.data.remote.RemoteSource
import kotlinx.coroutines.flow.flow

class FakeRemoteSource : RemoteSource {
    override fun getRepositoryList() = flow {
        emit(FakeData.fakeRepositoryListResponse)
    }

    override fun getFullRepositoryDetails(
        ownerName: String,
        repoName: String
    ) = flow {
        emit(FakeData.fakeRepositoryDetailsResponse)
    }

    override fun getRepositoryIssues(
        ownerName: String,
        repoName: String
    ) = flow {
        emit(FakeData.fakeRepositoryIssuesListResponse)
    }
}
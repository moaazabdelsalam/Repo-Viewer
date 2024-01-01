package com.task.githuprepoviewer.repotesting

import com.task.githuprepoviewer.data.local.LocalSource
import com.task.githuprepoviewer.data.remote.RemoteSource
import com.task.githuprepoviewer.data.repo.Repo
import com.task.githuprepoviewer.data.repo.convertToLocalRepositoryItemsList
import com.task.githuprepoviewer.data.repo.convertToRepositoryDetails
import com.task.githuprepoviewer.data.repo.convertToRepositoryIssuesList
import com.task.githuprepoviewer.data.repo.convertToRepositoryItemsList
import com.task.githuprepoviewer.datasorce.FakeData
import com.task.githuprepoviewer.datasorce.FakeLocalSource
import com.task.githuprepoviewer.datasorce.FakeRemoteSource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FakeRepo : Repo {
    private val remoteSource: RemoteSource = FakeRemoteSource()
    private val localSource: LocalSource = FakeLocalSource()

    override suspend fun updateLocalRepositoryList() {
        localSource.updateLocalRepositoryList(
            FakeData.fakeRepositoryListResponse.convertToLocalRepositoryItemsList()
        )
    }

    override fun getRepositoryList(stopIndex: Int) = flow {
        val localList = localSource.getLocalRepositoryList()
        emit(
            localList
                .subList(0, if (localList.size > stopIndex) stopIndex else localList.size)
                .convertToRepositoryItemsList()
        )
    }

    override fun getFullRepositoryDetails(
        ownerName: String,
        repoName: String
    ) = remoteSource.getFullRepositoryDetails(ownerName, repoName).map {
        it.convertToRepositoryDetails()
    }

    override fun getRepositoryIssues(
        ownerName: String,
        repoName: String
    ) = remoteSource.getRepositoryIssues(ownerName, repoName).map {
        it.convertToRepositoryIssuesList()
    }
}
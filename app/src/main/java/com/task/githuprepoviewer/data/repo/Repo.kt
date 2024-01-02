package com.task.githuprepoviewer.data.repo

import com.task.githuprepoviewer.presentation.details.RepositoryDetails
import com.task.githuprepoviewer.presentation.home.HomeRepositoryItem
import com.task.githuprepoviewer.presentation.issues.RepositoryIssuesItem
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun updateLocalRepositoryList()
    fun getRepositoryList(stopIndex: Int): Flow<List<HomeRepositoryItem>>
    fun getFullRepositoryDetails(ownerName: String, repoName: String): Flow<RepositoryDetails>
    fun getRepositoryIssues(ownerName: String, repoName: String): Flow<List<RepositoryIssuesItem>>
}
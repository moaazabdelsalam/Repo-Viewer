package com.task.githuprepoviewer.repotesting

import com.task.githuprepoviewer.data.local.db.LocalRepositoryItem
import com.task.githuprepoviewer.data.repo.Repo
import com.task.githuprepoviewer.data.repo.convertToRepositoryDetails
import com.task.githuprepoviewer.data.repo.convertToRepositoryIssuesList
import com.task.githuprepoviewer.data.repo.convertToRepositoryItem
import com.task.githuprepoviewer.data.repo.convertToRepositoryItemsList
import com.task.githuprepoviewer.datasorce.FakeData
import com.task.githuprepoviewer.presentation.details.RepositoryDetails
import com.task.githuprepoviewer.presentation.home.HomeRepositoryItem
import com.task.githuprepoviewer.presentation.issues.RepositoryIssuesItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RepoTest {
    private lateinit var repo: Repo

    @Before
    fun setup() {
        repo = FakeRepo()
    }

    @Test
    fun updateLocalRepositoryList_success() = runBlocking {
        repo.updateLocalRepositoryList()
        assert(FakeData.fakeLocalRepositoryList.size == 1)
    }

    @Test
    fun updateLocalRepositoryList_correct() = runBlocking {
        repo.updateLocalRepositoryList()
        val repositoryItem: LocalRepositoryItem =
            FakeData.fakeRepositoryDetailsResponse.convertToRepositoryItem()
        assert(FakeData.fakeLocalRepositoryList.contains(repositoryItem))
    }

    @Test
    fun getRepositoryList_success() = runBlocking {
        val homeList = mutableListOf<HomeRepositoryItem>()
        repo.updateLocalRepositoryList()
        val job = launch {
            repo.getRepositoryList(1).collectLatest {
                homeList.addAll(it)
            }
        }
        delay(1000L)
        assert(homeList == FakeData.fakeLocalRepositoryList.convertToRepositoryItemsList())
        job.cancel()
    }

    @Test
    fun getFullRepositoryDetails_success() = runBlocking{
        var result: RepositoryDetails? = null
        val job = launch {
            repo.getFullRepositoryDetails("fakeuser", "fakerepo").collectLatest {
                result = it
            }
        }
        delay(1000L)
        assert(result == FakeData.fakeRepositoryDetailsResponse.convertToRepositoryDetails())
        job.cancel()
    }

    @Test
    fun getRepositoryIssues_success() = runBlocking{
        var result: List<RepositoryIssuesItem>? = null
        val job = launch {
            repo.getRepositoryIssues("fakeuser", "fakerepo").collectLatest {
                result = it
            }
        }
        delay(1000L)
        assert(result == FakeData.fakeRepositoryIssuesListResponse.convertToRepositoryIssuesList())
        job.cancel()
    }
}
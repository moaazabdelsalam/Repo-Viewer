package com.task.githuprepoviewer.viewmodeltesting

import androidx.lifecycle.SavedStateHandle
import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.data.repo.Repo
import com.task.githuprepoviewer.data.repo.convertToRepositoryIssuesList
import com.task.githuprepoviewer.datasorce.FakeData
import com.task.githuprepoviewer.domain.usecase.RepositoryIssuesUseCase
import com.task.githuprepoviewer.presentation.issues.IssuesViewModel
import com.task.githuprepoviewer.repotesting.FakeRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class IssuesViewModelTest {
    private lateinit var viewModel: IssuesViewModel
    private lateinit var repo: Repo
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var repositoryIssuesUseCase: RepositoryIssuesUseCase

    @Before
    fun setup() {
        repo = FakeRepo()
        repositoryIssuesUseCase = RepositoryIssuesUseCase(repo)
        savedStateHandle = SavedStateHandle()
        viewModel = IssuesViewModel(savedStateHandle, repositoryIssuesUseCase)
    }

    @Test
    fun getRepositoryDetails_success() = runBlocking {
        delay(1000L)
        val state = viewModel.repositoryIssuesState.value
        assert(state is ApiState.Success)
    }

    @Test
    fun getRepositoryDetails_correctData() = runBlocking {
        delay(1000L)
        val state = viewModel.repositoryIssuesState.value as? ApiState.Success
        val data = state?.data
        assert(data == FakeData.fakeRepositoryIssuesListResponse.convertToRepositoryIssuesList())
    }
}
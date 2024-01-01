package com.task.githuprepoviewer.viewmodeltesting

import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.data.repo.Repo
import com.task.githuprepoviewer.data.repo.convertToRepositoryItemsList
import com.task.githuprepoviewer.datasorce.FakeData
import com.task.githuprepoviewer.domain.usecase.RepositoryListUseCase
import com.task.githuprepoviewer.domain.usecase.UpdateLocalListUseCase
import com.task.githuprepoviewer.presentation.home.HomeViewModel
import com.task.githuprepoviewer.repotesting.FakeRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel
    private lateinit var repo: Repo
    private lateinit var updateLocalListUseCase: UpdateLocalListUseCase
    private lateinit var repositoryListUseCase: RepositoryListUseCase

    @Before
    fun setup() {
        repo = FakeRepo()
        updateLocalListUseCase = UpdateLocalListUseCase(repo)
        repositoryListUseCase = RepositoryListUseCase(repo)
        viewModel = HomeViewModel(updateLocalListUseCase, repositoryListUseCase)
    }

    @Test
    fun getHomeRepositoryList_success() = runBlocking{
        delay(1000L)
        val state = viewModel.repositoryListState.value
        assert(state is ApiState.Success)
    }

    @Test
    fun getHomeRepositoryList_correctData() = runBlocking {
        delay(1000L)
        val state = viewModel.repositoryListState.value as? ApiState.Success
        val data = state?.data
        assert(data == FakeData.fakeLocalRepositoryList.convertToRepositoryItemsList())
    }
}
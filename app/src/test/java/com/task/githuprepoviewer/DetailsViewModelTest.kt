package com.task.githuprepoviewer

import androidx.lifecycle.SavedStateHandle
import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.data.repo.Repo
import com.task.githuprepoviewer.data.repo.convertToRepositoryDetails
import com.task.githuprepoviewer.datasorce.FakeData
import com.task.githuprepoviewer.domain.usecase.RepositoryDetailsUseCase
import com.task.githuprepoviewer.presentation.details.DetailsViewModel
import com.task.githuprepoviewer.repotesting.FakeRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DetailsViewModelTest {
    private lateinit var viewModel: DetailsViewModel
    private lateinit var repo: Repo
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var repositoryDetailsUseCase: RepositoryDetailsUseCase

    @Before
    fun setup() {
        repo = FakeRepo()
        repositoryDetailsUseCase = RepositoryDetailsUseCase(repo)
        savedStateHandle = SavedStateHandle()
        viewModel = DetailsViewModel(savedStateHandle, repositoryDetailsUseCase)
    }

    @Test
    fun getRepositoryDetails_success() = runBlocking {
        delay(1000L)
        val state = viewModel.repositoryDetailsState.value
        assert(state is ApiState.Success)
    }

    @Test
    fun getRepositoryDetails_correctData() = runBlocking {
        delay(1000L)
        val state = viewModel.repositoryDetailsState.value as? ApiState.Success
        val data = state?.data
        assert(data == FakeData.fakeRepositoryDetailsResponse.convertToRepositoryDetails())
    }
}
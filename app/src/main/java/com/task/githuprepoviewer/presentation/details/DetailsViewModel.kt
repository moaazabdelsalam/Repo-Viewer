package com.task.githuprepoviewer.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.domain.usecase.RepositoryDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repositoryDetailsUseCase: RepositoryDetailsUseCase
) : ViewModel() {

    private val _repositoryDetailsState: MutableStateFlow<ApiState<RepositoryDetails>> =
        MutableStateFlow(ApiState.Loading)
    val repositoryDetailsState: StateFlow<ApiState<RepositoryDetails>>
        get() = _repositoryDetailsState.asStateFlow()

    init {
        val ownerName = savedStateHandle.get<String>("owner") ?: " "
        val repoName = savedStateHandle.get<String>("repo") ?: " "
        viewModelScope.launch(Dispatchers.IO) {
            _repositoryDetailsState.value = ApiState.Loading
            repositoryDetailsUseCase(ownerName, repoName).catch {
                _repositoryDetailsState.value = ApiState.Failure("${it.message}")
            }.collectLatest {
                _repositoryDetailsState.value = ApiState.Success(it)
            }
        }
    }
}
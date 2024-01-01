package com.task.githuprepoviewer.presentation.issues

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.githuprepoviewer.Constants
import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.domain.usecase.RepositoryIssuesUseCase
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
class IssuesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repositoryIssuesUseCase: RepositoryIssuesUseCase
) : ViewModel() {

    private val _repositoryIssuesState: MutableStateFlow<ApiState<List<RepositoryIssuesItem>>> =
        MutableStateFlow(ApiState.Loading)
    val repositoryIssuesState: StateFlow<ApiState<List<RepositoryIssuesItem>>>
        get() = _repositoryIssuesState.asStateFlow()

    init {
        val ownerName = savedStateHandle.get<String>("owner") ?: " "
        val repoName = savedStateHandle.get<String>("repo") ?: " "
        viewModelScope.launch(Dispatchers.IO) {
            _repositoryIssuesState.value = ApiState.Loading
            repositoryIssuesUseCase(ownerName, repoName).catch {
                _repositoryIssuesState.value = ApiState.Failure(Constants.ERROR_MSG)
            }.collectLatest {
                _repositoryIssuesState.value = ApiState.Success(it)
            }
        }
    }
}
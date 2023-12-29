package com.task.githuprepoviewer.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.domain.usecase.RepositoryListUseCase
import com.task.githuprepoviewer.presentation.RepositoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repositoryListUseCase: RepositoryListUseCase
) : ViewModel() {

    private val _repositoryListState: MutableStateFlow<ApiState<List<RepositoryItem>>> =
        MutableStateFlow(ApiState.Loading)
    val repositoryListState: StateFlow<ApiState<List<RepositoryItem>>>
        get() = _repositoryListState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryListUseCase().collectLatest {
                _repositoryListState.value = it
            }
        }
    }
}
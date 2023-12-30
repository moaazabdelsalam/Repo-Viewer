package com.task.githuprepoviewer.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.githuprepoviewer.data.remote.ApiState
import com.task.githuprepoviewer.domain.usecase.RepositoryListUseCase
import com.task.githuprepoviewer.domain.usecase.UpdateLocalListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val updateLocalListUseCase: UpdateLocalListUseCase,
    private val repositoryListUseCase: RepositoryListUseCase
) : ViewModel() {

    private val TAG = "TAG HomeViewModel"
    private val _repositoryListState: MutableStateFlow<ApiState<List<HomeRepositoryItem>>> =
        MutableStateFlow(ApiState.Loading)
    val repositoryListState: StateFlow<ApiState<List<HomeRepositoryItem>>>
        get() = _repositoryListState.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        Log.i(TAG, "exception: ${throwable.message.toString()}")
        _repositoryListState.value =
            ApiState.Failure(throwable.message.toString())
    }

    init {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            updateLocalListUseCase()
            repositoryListUseCase().collectLatest {
                _repositoryListState.value = ApiState.Success(it)
            }
        }
    }
}
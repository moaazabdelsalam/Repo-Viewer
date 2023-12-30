package com.task.githuprepoviewer.presentation.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.githuprepoviewer.domain.usecase.RepositoryDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repositoryDetailsUseCase: RepositoryDetailsUseCase
) : ViewModel() {

    init {
        val ownerName = savedStateHandle.get<String>("owner") ?: " "
        val repoName = savedStateHandle.get<String>("repo") ?: " "
        viewModelScope.launch(Dispatchers.IO) {
            repositoryDetailsUseCase(ownerName, repoName).catch {
                Log.i("TAG DetailsViewModel", "exception: ${it.message}")
            }.collectLatest {
                Log.i("TAG DetailsViewModel", "data: $it")
            }
        }
    }
}
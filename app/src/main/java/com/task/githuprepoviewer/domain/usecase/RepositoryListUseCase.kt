package com.task.githuprepoviewer.domain.usecase

import com.task.githuprepoviewer.data.repo.Repo
import com.task.githuprepoviewer.presentation.home.HomeRepositoryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryListUseCase @Inject constructor(
    private val repo: Repo
) {
    operator fun invoke(stopIndex: Int): Flow<List<HomeRepositoryItem>> {
        return repo.getRepositoryList(stopIndex)
    }
}
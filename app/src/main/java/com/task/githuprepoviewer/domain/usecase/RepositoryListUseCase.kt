package com.task.githuprepoviewer.domain.usecase

import com.task.githuprepoviewer.data.repo.Repo
import com.task.githuprepoviewer.presentation.RepositoryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryListUseCase @Inject constructor(
    private val repo: Repo
) {
    operator fun invoke(): Flow<List<RepositoryItem>> {
        return repo.getRepositoryList()
    }
}
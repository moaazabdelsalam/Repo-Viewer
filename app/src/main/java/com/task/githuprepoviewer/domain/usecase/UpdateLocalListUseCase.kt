package com.task.githuprepoviewer.domain.usecase

import com.task.githuprepoviewer.data.repo.Repo
import javax.inject.Inject

class UpdateLocalListUseCase @Inject constructor(
    private val repo: Repo
) {
    suspend operator fun invoke() = repo.updateLocalRepositoryList()
}
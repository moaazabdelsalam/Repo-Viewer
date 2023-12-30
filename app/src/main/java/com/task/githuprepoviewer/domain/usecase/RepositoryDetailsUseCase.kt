package com.task.githuprepoviewer.domain.usecase

import com.task.githuprepoviewer.data.repo.Repo
import javax.inject.Inject

class RepositoryDetailsUseCase @Inject constructor(
    private val repo: Repo
) {
    operator fun invoke(ownerName: String, repoName: String) =
        repo.getFullRepositoryDetails(ownerName, repoName)
}
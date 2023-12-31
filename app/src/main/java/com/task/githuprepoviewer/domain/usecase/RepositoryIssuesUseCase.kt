package com.task.githuprepoviewer.domain.usecase

import com.task.githuprepoviewer.data.repo.Repo
import javax.inject.Inject

class RepositoryIssuesUseCase @Inject constructor(
    private val repo: Repo
) {
    operator fun invoke(ownerName: String, repoName: String) =
        repo.getRepositoryIssues(ownerName, repoName)
}
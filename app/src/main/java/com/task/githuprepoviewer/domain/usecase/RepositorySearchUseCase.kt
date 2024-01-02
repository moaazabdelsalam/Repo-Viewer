package com.task.githuprepoviewer.domain.usecase

import com.task.githuprepoviewer.data.repo.Repo
import com.task.githuprepoviewer.presentation.home.HomeRepositoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositorySearchUseCase @Inject constructor(
    private val repo: Repo
) {

    operator fun invoke(searchText: String): Flow<List<HomeRepositoryItem>> {
        return repo.getRepositoryList(100).map {list ->
            list.filter {item ->
                item.ownerName.contains(searchText, ignoreCase = true) ||
                        item.repoName.contains(searchText, ignoreCase = true)
            }
        }
    }
}
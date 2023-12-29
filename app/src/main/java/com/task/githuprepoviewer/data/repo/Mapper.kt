package com.task.githuprepoviewer.data.repo

import com.task.githuprepoviewer.data.remote.model.RepositoryResponse
import com.task.githuprepoviewer.presentation.RepositoryItem

fun RepositoryResponse.convertToRepositoryItem(): RepositoryItem =
    RepositoryItem(
        ownerName = owner.login,
        ownerAvatarUrl = owner.avatarUrl,
        repoName = name,
        repoDescription = description
    )
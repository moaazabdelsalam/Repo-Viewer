package com.task.githuprepoviewer.data.repo

import com.task.githuprepoviewer.data.local.db.LocalRepositoryItem
import com.task.githuprepoviewer.data.remote.model.RepositoryResponse
import com.task.githuprepoviewer.presentation.RepositoryItem

fun RepositoryResponse.convertToRepositoryItem(): RepositoryItem =
    RepositoryItem(
        ownerName = owner.login,
        ownerAvatarUrl = owner.avatarUrl,
        repoName = name,
        repoDescription = description
    )

fun List<RepositoryResponse>.convertToLocalRepositoryItemsList(): List<LocalRepositoryItem> =
    map {
        LocalRepositoryItem(
            id = it.id,
            ownerName = it.owner.login,
            ownerAvatarUrl = it.owner.avatarUrl,
            repoName = it.name,
            repoDescription = it.description
        )
    }

fun List<LocalRepositoryItem>.convertToRepositoryItemsList(): List<RepositoryItem> =
    map {
        RepositoryItem(
            ownerName = it.ownerName,
            ownerAvatarUrl = it.ownerAvatarUrl,
            repoName = it.repoName,
            repoDescription = it.repoDescription
        )
    }
package com.task.githuprepoviewer.data.repo

import com.task.githuprepoviewer.data.local.db.LocalRepositoryItem
import com.task.githuprepoviewer.data.remote.model.RepositoryDetailsResponse
import com.task.githuprepoviewer.data.remote.model.RepositoryIssuesResponse
import com.task.githuprepoviewer.presentation.details.RepositoryDetails
import com.task.githuprepoviewer.presentation.home.HomeRepositoryItem
import com.task.githuprepoviewer.presentation.issues.RepositoryIssuesItem

/*fun RepositoryResponse.convertToRepositoryItem(): RepositoryItem =
    RepositoryItem(
        ownerName = owner.login,
        ownerAvatarUrl = owner.avatarUrl,
        repoName = name,
        repoDescription = description
    )*/

fun List<RepositoryDetailsResponse>.convertToLocalRepositoryItemsList(): List<LocalRepositoryItem> =
    map {
        LocalRepositoryItem(
            id = it.id,
            ownerName = it.owner.login,
            ownerAvatarUrl = it.owner.avatarUrl,
            repoName = it.name,
            repoDescription = it.description
        )
    }

fun List<LocalRepositoryItem>.convertToRepositoryItemsList(): List<HomeRepositoryItem> =
    map {
        HomeRepositoryItem(
            ownerName = it.ownerName,
            ownerAvatarUrl = it.ownerAvatarUrl,
            repoName = it.repoName,
            repoDescription = it.repoDescription
        )
    }

fun RepositoryDetailsResponse.convertToRepositoryResponse(): RepositoryDetails =
    RepositoryDetails(
        ownerName = owner.login,
        ownerAvatarUrl = owner.avatarUrl,
        ownerType = owner.type,
        repoName = name,
        repoDescription = description,
        isPrivate = isPrivate,
        stargazersCount = stargazersCount,
        watchersCount = watchersCount,
        forksCount = forksCount,
        language = language,
        topics = topics,
        openIssuesCount = openIssuesCount,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

fun List<RepositoryIssuesResponse>.convertToRepositoryIssuesList(): List<RepositoryIssuesItem> =
    map {
        RepositoryIssuesItem(
            author = it.user.login,
            authorAvatarUrl = it.user.avatarUrl,
            issueTitle = it.title,
            description = it.body,
            date = it.createdAt,
            state = it.state
        )
    }
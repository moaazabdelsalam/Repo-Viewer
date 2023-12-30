package com.task.githuprepoviewer.presentation.details

data class RepositoryDetails(
    val ownerName: String,
    val ownerAvatarUrl: String,
    val ownerType: String,
    val repoName: String,
    val repoDescription: String?,
    val isPrivate: Boolean,
    val stargazersCount: Int,
    val watchersCount: Int,
    val forksCount: Int,
    val language: String,
    val topics: List<String>,
    val openIssuesCount: Int,
    val createdAt: String,
    val updatedAt: String
)

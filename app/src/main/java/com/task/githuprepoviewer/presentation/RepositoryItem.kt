package com.task.githuprepoviewer.presentation

data class RepositoryItem(
    val ownerName: String,
    val ownerAvatarUrl: String,
    val repoName: String,
    val repoDescription: String?,
    val starsCount: Int = 0
)

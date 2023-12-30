package com.task.githuprepoviewer.presentation.home

data class HomeRepositoryItem(
    val ownerName: String,
    val ownerAvatarUrl: String,
    val repoName: String,
    val repoDescription: String?,
    val starsCount: Int = 0,
    val language: String = "",
)

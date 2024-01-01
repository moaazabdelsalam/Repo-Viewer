package com.task.githuprepoviewer.presentation.issues

data class RepositoryIssuesItem(
    val author: String,
    val authorAvatarUrl: String,
    val issueTitle: String,
    val description: String?,
    val date: String,
    val state: String
)

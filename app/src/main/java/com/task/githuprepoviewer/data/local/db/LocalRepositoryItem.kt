package com.task.githuprepoviewer.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repository_table")
data class LocalRepositoryItem(
    @PrimaryKey
    val id: Int,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val repoName: String,
    val repoDescription: String?,
    val starsCount: Int = 0
)

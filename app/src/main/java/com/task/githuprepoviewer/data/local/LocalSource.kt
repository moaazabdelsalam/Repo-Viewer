package com.task.githuprepoviewer.data.local

import com.task.githuprepoviewer.data.local.db.LocalRepositoryItem

interface LocalSource {
    suspend fun getLocalRepositoryList(): List<LocalRepositoryItem>
    suspend fun updateLocalRepositoryList(list: List<LocalRepositoryItem>)
}
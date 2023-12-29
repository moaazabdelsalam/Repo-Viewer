package com.task.githuprepoviewer.data.local

import com.task.githuprepoviewer.data.local.db.LocalRepositoryItem
import com.task.githuprepoviewer.data.local.db.RepositoryDao
import javax.inject.Inject

class LocalSourceImp @Inject constructor(
    private val dao: RepositoryDao
) : LocalSource {
    override suspend fun getLocalRepositoryList() = dao.getAll()

    override suspend fun updateLocalRepositoryList(list: List<LocalRepositoryItem>) {
        dao.addAll(list)
    }

}
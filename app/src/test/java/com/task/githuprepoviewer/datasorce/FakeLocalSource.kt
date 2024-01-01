package com.task.githuprepoviewer.datasorce

import com.task.githuprepoviewer.data.local.LocalSource
import com.task.githuprepoviewer.data.local.db.LocalRepositoryItem

class FakeLocalSource : LocalSource {
    override suspend fun getLocalRepositoryList() = FakeData.fakeLocalRepositoryList

    override suspend fun updateLocalRepositoryList(list: List<LocalRepositoryItem>) {
        FakeData.fakeLocalRepositoryList.addAll(list)
    }
}
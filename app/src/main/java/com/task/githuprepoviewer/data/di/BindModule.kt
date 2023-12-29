package com.task.githuprepoviewer.data.di

import com.task.githuprepoviewer.data.local.LocalSource
import com.task.githuprepoviewer.data.local.LocalSourceImp
import com.task.githuprepoviewer.data.remote.RemoteSource
import com.task.githuprepoviewer.data.remote.RemoteSourceImp
import com.task.githuprepoviewer.data.repo.Repo
import com.task.githuprepoviewer.data.repo.RepoImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Binds
    abstract fun bindRepo(
        repoImp: RepoImp
    ): Repo

    @Binds
    abstract fun bindRemoteSource(
        remoteSourceImp: RemoteSourceImp
    ): RemoteSource

    @Binds
    abstract fun bindLocalSource(
        localSourceImp: LocalSourceImp
    ): LocalSource
}
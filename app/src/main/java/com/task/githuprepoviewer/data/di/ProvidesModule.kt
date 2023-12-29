package com.task.githuprepoviewer.data.di

import android.content.Context
import androidx.room.Room
import com.task.githuprepoviewer.Constants
import com.task.githuprepoviewer.data.local.db.RepositoryDao
import com.task.githuprepoviewer.data.local.db.RepositoryDatabase
import com.task.githuprepoviewer.data.remote.GitHubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {
    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): GitHubService {
        return retrofit.create(GitHubService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideRoomDao(
        database: RepositoryDatabase
    ): RepositoryDao {
        return database.getRepositoryDao()
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): RepositoryDatabase {
        return Room.databaseBuilder(
            context,
            RepositoryDatabase::class.java,
            Constants.DATA_BASE
        ).build()
    }
}
package com.task.githuprepoviewer.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalRepositoryItem::class], version = 1, exportSchema = false)
abstract class RepositoryDatabase : RoomDatabase() {
    abstract fun getRepositoryDao(): RepositoryDao
}
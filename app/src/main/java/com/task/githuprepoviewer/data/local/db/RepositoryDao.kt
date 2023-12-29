package com.task.githuprepoviewer.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repository_table")
    suspend fun getAll(): List<LocalRepositoryItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(prayerTimes: List<LocalRepositoryItem>)
}
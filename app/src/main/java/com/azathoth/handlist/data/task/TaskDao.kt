package com.azathoth.handlist.data.task

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM task WHERE id = :id")
    fun getItem(id: Int): Flow<Task>

    @Query("SELECT * FROM task ORDER BY name ASC")
    fun getAllItems(): Flow<List<Task>>
}
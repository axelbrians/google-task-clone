package com.machina.google_task_clone.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM taskTable WHERE isCompleted = 0")
    fun getUncompletedTask(): List<Task>

    @Query("SELECT * FROM taskTable WHERE isCompleted = 1")
    fun getCompletedTask(): List<Task>

    @Query("SELECT * FROM taskTable ORDER BY isCompleted")
    fun getAllTask(): LiveData<List<Task>>
}
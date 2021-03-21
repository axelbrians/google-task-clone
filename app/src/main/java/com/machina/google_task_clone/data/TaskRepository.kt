package com.machina.google_task_clone.data

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {

    val getAllTask: LiveData<List<Task>> = taskDao.getAllTask()

    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}
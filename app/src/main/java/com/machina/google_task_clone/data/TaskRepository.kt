package com.machina.google_task_clone.data

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {

    val getAllTask: LiveData<List<Task>> = taskDao.getAllTask()
    val getUncompletedTask: LiveData<List<Task>> = taskDao.getUncompletedTask()
    val getCompletedTask: LiveData<List<Task>> = taskDao.getCompletedTask()

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
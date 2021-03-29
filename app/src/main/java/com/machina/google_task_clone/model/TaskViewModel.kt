package com.machina.google_task_clone.model

import android.app.Application
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.machina.google_task_clone.R
import com.machina.google_task_clone.data.Task
import com.machina.google_task_clone.data.TaskDatabase
import com.machina.google_task_clone.data.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {

    private val repository: TaskRepository
    val getAllTask: LiveData<List<Task>>
    val getCompletedTask: LiveData<List<Task>>
    val getUncompletedTask: LiveData<List<Task>>


    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        getAllTask = repository.getAllTask
        getCompletedTask = repository.getCompletedTask
        getUncompletedTask = repository.getUncompletedTask
    }

    fun resolveCheckIcon(isCompleted: Boolean, view: ImageView) {
        if (!isCompleted) {
            val colorStateList = ColorStateList.valueOf(Color.parseColor("#A3A3A3"))
            ImageViewCompat.setImageTintList(view, colorStateList)
            view.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

}
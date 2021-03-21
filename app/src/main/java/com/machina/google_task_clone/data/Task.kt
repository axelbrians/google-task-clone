package com.machina.google_task_clone.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "taskTable")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val subTitle: String,
    val isCompleted: Boolean = false
)

package com.machina.google_task_clone.`interface`

import com.machina.google_task_clone.data.Task

interface OnCompletedTask {
    fun completedTask(task: Task)
}
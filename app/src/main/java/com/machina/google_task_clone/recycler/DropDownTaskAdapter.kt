package com.machina.google_task_clone.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.machina.google_task_clone.R
import com.machina.google_task_clone.data.Task
import com.machina.google_task_clone.databinding.VhTaskBinding

class DropDownTaskAdapter(
        private var data: List<Task>,
        private val updateTask: (Task) -> Unit,
        private val showSnackbar: (Task, ImageView) -> Unit): RecyclerView.Adapter<TaskVh>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVh {
        val binding = VhTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskVh(binding)
    }

    override fun onBindViewHolder(holder: TaskVh, position: Int) {
        holder.onBind(data[position], updateTask, showSnackbar)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
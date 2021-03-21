package com.machina.google_task_clone.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.machina.google_task_clone.R
import com.machina.google_task_clone.data.Task
import com.machina.google_task_clone.databinding.VhTaskBinding
import kotlin.random.Random

class TaskAdapter: RecyclerView.Adapter<TaskBaseViewHolder>() {
    private var data = emptyList<Task>()

    init { setHasStableIds(true) }

    fun setData(newData: List<Task>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskBaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.vh_task -> {
                val binding = VhTaskBinding.inflate(inflater, parent, false)
                TaskVh(binding)
            }
            else -> {
                val binding = VhTaskBinding.inflate(inflater, parent, false)
                TaskVh(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: TaskBaseViewHolder, position: Int) {

        when (holder) {
            is TaskVh -> { holder.onBind(data[position]) }
        }
        Log.d(TAG, "onBind size:${data.size}")
    }

    override fun getItemId(position: Int): Long {
        return data[position].id.toLong()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.vh_task
    }

    companion object {
        private const val TAG = "TaskAdapter"
    }
}

class TaskVh(private val binding: VhTaskBinding): TaskBaseViewHolder(binding.root) {

    fun onBind(task: Task) {
        binding.vhTaskTitle.text = task.title
    }
}
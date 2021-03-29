package com.machina.google_task_clone.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.machina.google_task_clone.R
import com.machina.google_task_clone.data.Task
import com.machina.google_task_clone.databinding.VhTaskBinding
import com.machina.google_task_clone.databinding.VhTaskDropDownBinding

class TaskAdapter(
        private val updateTask: (Task) -> Unit,
        private val showSnackbar: (Task, ImageView) -> Unit): RecyclerView.Adapter<TaskBaseViewHolder>() {

    private var data = mutableListOf<Task>()
    private var completedTask = emptyList<Task>()
    private val headerTask = Task(-1, "", "")
    private val bottomTask = Task(-2, "", "")

    init {
        setHasStableIds(true)
    }

    fun setUncompletedTask(newData: List<Task>) {
        data.remove(headerTask)
        data.remove(bottomTask)
        data = newData as MutableList<Task>
        data.add(headerTask)
        data.add(bottomTask)
        notifyDataSetChanged()
    }

    fun setCompletedTask(newData: List<Task>) {
        completedTask = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskBaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.vh_task_drop_down -> {
                val binding = VhTaskDropDownBinding.inflate(inflater, parent, false)
                TaskDropDownVh(binding)
            }
            else -> {
                val binding = VhTaskBinding.inflate(inflater, parent, false)
                TaskVh(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: TaskBaseViewHolder, position: Int) {

        when (holder) {
            is TaskVh -> { holder.onBind(data[position], updateTask, showSnackbar) }
            is TaskDropDownVh -> { holder.onBind(completedTask, updateTask, showSnackbar) }
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
        return when (data[position].id) {
            -1 -> { R.layout.vh_task_drop_down }
            else -> { R.layout.vh_task }
        }
    }

    companion object {
        private const val TAG = "TaskAdapter"
    }
}

class TaskDropDownVh(private val binding: VhTaskDropDownBinding): TaskBaseViewHolder(binding.root) {

    private var isExpanded = true
    private val recyclerView = binding.vhTaskDropDownRecycler

    fun onBind(
            completedTask: List<Task>,
            updateTask: (Task) -> Unit,
            showSnackbar: (Task, ImageView) -> Unit) {
        val mAdapter = DropDownTaskAdapter(completedTask, updateTask, showSnackbar)
        val mLayoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)

        recyclerView.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
            itemAnimator = DefaultItemAnimator()
        }


        binding.vhTaskDropDownArrow.setOnClickListener {
            if (isExpanded) {
                isExpanded = !isExpanded
                recyclerView.visibility = View.GONE
            } else {
                isExpanded = !isExpanded
                recyclerView.visibility = View.VISIBLE
            }
        }
    }

}
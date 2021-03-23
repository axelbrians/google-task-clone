package com.machina.google_task_clone.recycler

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.machina.google_task_clone.R
import com.machina.google_task_clone.data.Task
import com.machina.google_task_clone.databinding.VhTaskBinding

class TaskAdapter(
        private val updateTask: (Task) -> Unit,
        private val showSnackbar: (Task, ImageView) -> Unit)
    : RecyclerView.Adapter<TaskBaseViewHolder>() {
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
            is TaskVh -> { holder.onBind(data[position], updateTask, showSnackbar) }
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

    private var isChecked = false
    private var checkImg = binding.vhTaskCheck
    private lateinit var avdCompat: AnimatedVectorDrawableCompat
    private lateinit var avd: AnimatedVectorDrawable

    fun onBind(task: Task,
               updateTask: (Task) -> Unit,
               showSnackbar: (Task, ImageView) -> Unit) {

        binding.vhTaskTitle.text = task.title
        resolveCheckIcon(task.isCompleted)

        checkImg.setOnClickListener { view ->
            val newTask = task.copy(isCompleted = task.isCompleted.not())
            animateCheck()
            updateTask(newTask)
            showSnackbar(task, view as ImageView)
        }
    }

    private fun resolveCheckIcon(completed: Boolean) {
        if (completed) {
            checkImg.setImageResource(R.drawable.ic_baseline_check_24)
            changeColor(BLUE_ACCENT)
        } else {
            checkImg.setImageResource(R.drawable.avd_circle_to_check)
            changeColor(GREY_ICON)
        }
    }

    private fun animateCheck() {
        if (isChecked) {
            checkImg.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
            changeColor(GREY_ICON)
            isChecked = !isChecked
        } else {
            checkImg.setImageResource(R.drawable.avd_circle_to_check)
            val drawable = checkImg.drawable

            if (drawable is AnimatedVectorDrawableCompat) {
                avdCompat = drawable
                avdCompat.start()
                changeColor(BLUE_ACCENT)
            } else {
                avd = drawable as AnimatedVectorDrawable
                avd.start()
                changeColor(BLUE_ACCENT)
            }
            isChecked = !isChecked
        }
    }

    private fun changeColor(colorString: String) {
        val colorStateList = ColorStateList.valueOf(Color.parseColor(colorString))
        ImageViewCompat.setImageTintList(checkImg, colorStateList)
    }

    companion object {
        private const val BLUE_ACCENT = "#88B4FF"
        private const val GREY_ICON = "#A3A3A3"
    }
}
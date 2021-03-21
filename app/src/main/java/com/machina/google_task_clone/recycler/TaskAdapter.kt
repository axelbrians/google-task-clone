package com.machina.google_task_clone.recycler

import android.graphics.drawable.AnimatedVectorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
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

    private var isChecked = false
    private var checkImg = binding.vhTaskCheck
    private lateinit var avdCompat: AnimatedVectorDrawableCompat
    private lateinit var avd: AnimatedVectorDrawable

    fun onBind(task: Task) {
        binding.vhTaskTitle.text = task.title
        checkImg.setOnClickListener { view -> animateCheck()}
    }

    private fun animateCheck() {
        if (isChecked) {
            checkImg.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
            isChecked = !isChecked
        } else {
            checkImg.setImageResource(R.drawable.avd_circle_to_check)
            val drawable = checkImg.drawable

            if (drawable is AnimatedVectorDrawableCompat) {
                avdCompat = drawable
                avdCompat.start()
            } else {
                avd = drawable as AnimatedVectorDrawable
                avd.start()
            }
            isChecked = !isChecked
        }
    }
}
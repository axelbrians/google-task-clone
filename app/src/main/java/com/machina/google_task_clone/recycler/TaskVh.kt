package com.machina.google_task_clone.recycler

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.machina.google_task_clone.R
import com.machina.google_task_clone.data.Task
import com.machina.google_task_clone.databinding.VhTaskBinding
import java.util.*

class TaskVh(private val binding: VhTaskBinding): TaskBaseViewHolder(binding.root) {

    private var isChecked = false
    private var checkImg = binding.vhTaskCheck
    private lateinit var avdCompat: AnimatedVectorDrawableCompat
    private lateinit var avd: AnimatedVectorDrawable

    fun onBind(task: Task,
               updateTask: (Task) -> Unit,
               showSnackbar: (Task, ImageView) -> Unit) {
        isChecked = task.isCompleted
        resolveVisibility(task.id)
        resolveCheckIcon(task.isCompleted)
        binding.vhTaskTitle.text = task.title

        checkImg.setOnClickListener { view ->
            val newTask = task.copy(isCompleted = task.isCompleted.not())
            if (isChecked.not()) {
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        updateTask(newTask)
                    }
                }, 700)
            } else updateTask(newTask)

            animateCheck()
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

    private fun resolveVisibility(id: Int) {
        if (id == -2) binding.vhTaskContainer.visibility = View.INVISIBLE
        else binding.vhTaskContainer.visibility = View.VISIBLE
    }

    companion object {
        private const val BLUE_ACCENT = "#88B4FF"
        private const val GREY_ICON = "#A3A3A3"
    }
}
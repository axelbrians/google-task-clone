package com.machina.google_task_clone.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.machina.google_task_clone.MainActivity
import com.machina.google_task_clone.R
import com.machina.google_task_clone.data.Task
import com.machina.google_task_clone.databinding.FragmentHomeBinding
import com.machina.google_task_clone.model.TaskViewModel
import com.machina.google_task_clone.recycler.TaskAdapter

class FragmentHome: Fragment() {
    private var _binding : FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private val viewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val mAdapter = TaskAdapter(viewModel::updateTask, this::showSnackbar)
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        binding.fragmentHomeRecycler.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
            itemAnimator = DefaultItemAnimator()
        }

        viewModel.getAllTask.observe(viewLifecycleOwner, { allTask ->
            mAdapter.setData(allTask)
        })

        return binding.root

    }

    private fun showSnackbar(task: Task, view: ImageView) {
        Snackbar.make(binding.fragmentHomeRecycler, "Text label", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    Log.d(TAG, "undo checking task")
                    viewModel.updateTask(task)
                    viewModel.resolveCheckIcon(task.isCompleted, view)
                }
                .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "FragmentHome"
    }
}
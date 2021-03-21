package com.machina.google_task_clone.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
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
        val mAdapter = TaskAdapter()
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
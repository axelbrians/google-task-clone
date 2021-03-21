package com.machina.google_task_clone.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.machina.google_task_clone.databinding.BottomSheetMoreBinding

class BottomSheetMore: BottomSheetDialogFragment() {
    private var _binding: BottomSheetMoreBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomSheetMoreBinding.inflate(inflater, container, false)

        return binding.root
    }
}
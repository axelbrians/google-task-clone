package com.machina.google_task_clone.dialog

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.machina.google_task_clone.R
import com.machina.google_task_clone.`interface`.OnAddNewTask
import com.machina.google_task_clone.data.Task
import com.machina.google_task_clone.databinding.BottomSheetAddBinding
import com.machina.google_task_clone.model.TaskViewModel

class BottomSheetAdd(
        private val inputMethod: InputMethodManager)
    : BottomSheetDialogFragment() {

    private val viewModel: TaskViewModel by activityViewModels()
    private var _binding: BottomSheetAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var title: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomSheetAddBinding.inflate(inflater, container, false)

        initUI()


        return binding.root
    }

    private fun initUI() {
        title = binding.bottomSheetAddTitle
        saveButton = binding.bottomSheetAddSave

        title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                saveButton.isEnabled = !s.isNullOrBlank()

                if (s.isNullOrBlank()) saveButton.setTextColor(Color.parseColor("#5A5A5A"))
                else saveButton.setTextColor(Color.parseColor("#88B4FF"))
            }

            override fun afterTextChanged(s: Editable?) { }
        })


        saveButton.setOnClickListener {
            if (title.text.isNullOrEmpty().not()) {
                val task = Task(0, title.text.toString(), "")
                viewModel.addTask(task)
                dismiss()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showKeyboard()
    }

    private fun showKeyboard() {
        title.requestFocus()
        inputMethod.showSoftInput(title, InputMethodManager.SHOW_IMPLICIT)
    }
}
package com.machina.google_task_clone

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.machina.google_task_clone.`interface`.OnAddNewTask
import com.machina.google_task_clone.data.Task
import com.machina.google_task_clone.databinding.ActivityMainBinding
import com.machina.google_task_clone.dialog.BottomSheetAdd
import com.machina.google_task_clone.dialog.BottomSheetMore
import com.machina.google_task_clone.model.TaskViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        initiateUI()
        initiateAppBar()
        binding.activityMainAdd.setOnClickListener {
            val inputMethod = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val bottomSheet = BottomSheetAdd(inputMethod)
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }

    private fun initiateAppBar() {
        binding.activityMainBottomMenu.apply {
            setNavigationOnClickListener {
                Log.d(TAG, "AppBar Nav clicked")
            }

            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_main_more -> {
                        val bottomSheet = BottomSheetMore()
                        bottomSheet.show(supportFragmentManager, bottomSheet.tag)

                        true
                    }
                    else -> { false }
                }
            }
        }
    }

    private fun showSnackbar() {
        Snackbar.make(binding.activityMainBottomMenu, "Text label", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    Log.d(TAG, "undo checking task")
                }
                .setAnchorView(binding.activityMainAdd)
                .show()
    }

    private fun initiateUI() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.activityMainBottomMenu.replaceMenu(R.menu.menu_home)
        binding.activityMainBottomMenu.setupWithNavController(navController)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
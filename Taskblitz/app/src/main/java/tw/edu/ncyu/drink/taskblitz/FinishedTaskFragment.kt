package tw.edu.ncyu.drink.taskblitz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import tw.edu.ncyu.drink.taskblitz.databinding.FragmentFinishedTaskBinding
import java.util.*

class FinishedTaskFragment: Fragment() {
    private var _binding: FragmentFinishedTaskBinding?=null
    private val binding get() = _binding!!
    val viewModel : TaskViewModel  by viewModels()
    var selectedDate = getCurrentDate()
    private lateinit var taskAdapter: TaskAdapter


    //在 onCreateView 中定義 FirstFragment 的畫面為 fragment_first
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFinishedTaskBinding.inflate(inflater, container, false)

        // Observe the LiveData returned by the getAllTasks method
        viewModel.getFinishedTasks().observe(viewLifecycleOwner , Observer {  list->
            observeFinishedTasks(list)
            taskAdapter.onItemClick = { task ->
                // Handle item click here
                Log.d("taskClicked", "$task is clicked")
                val createTaskDialog = CreateTaskDialog()
                val arguments = Bundle().apply {
                    task.id?.let { putInt("id", it) }
                    putString("title", "編輯任務")
                    putString("name", task.title)
                    putString("description", task.description)
                    putString("category", task.category)
                    putString("date", task.date)
                    putString("time", task.time)
                    putInt("isFinished", task.isFinished)
                }
                createTaskDialog.arguments = arguments
                createTaskDialog.show(childFragmentManager, "EditTask")
            }
        })

        binding.tvSearchDate.setOnClickListener{
            // Show the date picker
            val dialogBuilder = AlertDialog.Builder(requireContext())
            val datePicker = DatePicker(context)
            dialogBuilder.setView(datePicker)
                .setPositiveButton("OK") { dialog, which ->
                    val selectedYear = datePicker.year
                    val selectedMonth = datePicker.month + 1
                    val selectedDayOfMonth = datePicker.dayOfMonth

                    // Handle the selected date
                    selectedDate = "${selectedYear.toString()}/${selectedMonth.toString()}/${selectedDayOfMonth.toString()}"
                    binding.tvSearchDate.text = selectedDate

                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }

            val dialog = dialogBuilder.create()
            dialog.show()
        }

        binding.btnSearch.setOnClickListener {
            // Handle search clicked event
            // Observe the LiveData returned by the getTasksByDate method
            viewModel.getFinishedTasksByDate(selectedDate).observe(viewLifecycleOwner , Observer {  list->
                Log.d("getAllTasks_selectedDate", list.toString())
                if (list.isEmpty()){
                    // no search result match the date and finished
                    binding.tvNotification.text = "該日期沒有完成的任務"
                    binding.tvNotification.visibility = View.VISIBLE
                } else {
                    binding.tvNotification.visibility = View.GONE
                }
                taskAdapter = TaskAdapter(viewModel, list)
                // set the layout manager and the adapter for the recycler view
                binding.recyclerView.layoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                binding.recyclerView.adapter = taskAdapter
            })
        }

        binding.btnClear.setOnClickListener {
            // clear the search result and print out all finished tasks
            binding.tvSearchDate.text = "搜尋日期"
            viewModel.getFinishedTasks().observe(viewLifecycleOwner, Observer { list ->
                observeFinishedTasks(list)
            })
        }

        return binding.root
    }

    private fun observeFinishedTasks(list: List<Tasks>){
        Log.d("getAllTasks_finished", list.toString())
        if (list.isEmpty()){
            // no finished tasks
            binding.tvNotification.text = "沒有完成的任務"
            binding.tvNotification.visibility = View.VISIBLE
        } else {
            binding.tvNotification.visibility = View.GONE
        }
        taskAdapter = TaskAdapter(viewModel, list)
        // set the layout manager and the adapter for the recycler view
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = taskAdapter
    }

    private fun getCurrentDate(): String {
        // Get the current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month =
            calendar.get(Calendar.MONTH) + 1 // Note: Calendar.MONTH is zero-based, so add 1 to get the actual month
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return "$year/$month/$day"
    }
}
package tw.edu.ncyu.drink.taskblitz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import tw.edu.ncyu.drink.taskblitz.databinding.FragmentInboxBinding

class InboxFragment: Fragment() {

    private var _binding: FragmentInboxBinding?=null
    private val binding get() = _binding!!
    val viewModel : TaskViewModel  by viewModels()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentInboxBinding.inflate(inflater, container, false)

        // Observe the LiveData returned by the getAllTasks method
        viewModel.getAllTasks().observe(viewLifecycleOwner , Observer {  list->
            Log.d("getAllTasks", list.toString())
            if(list.isEmpty()){
                // no tasks has been created
                binding.tvNotification.visibility = View.VISIBLE
            } else {
                binding.tvNotification.visibility = View.GONE
            }
            taskAdapter = TaskAdapter(viewModel, list)
            // set the layout manager and the adapter for the recycler view
            binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.adapter = taskAdapter

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

        return binding.root
    }
}

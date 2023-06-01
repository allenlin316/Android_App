package tw.edu.ncyu.drink.taskblitz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import tw.edu.ncyu.drink.taskblitz.databinding.FragmentFinishedTaskBinding

class FinishedTaskFragment: Fragment() {
    private var _binding: FragmentFinishedTaskBinding?=null
    private val binding get() = _binding!!
    val viewModel : TaskViewModel  by viewModels()
    private lateinit var taskAdapter: TaskAdapter


    //在 onCreateView 中定義 FirstFragment 的畫面為 fragment_first
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFinishedTaskBinding.inflate(inflater, container, false)

        // Observe the LiveData returned by the getAllTasks method
        viewModel.getFinishTasks().observe(viewLifecycleOwner , Observer {  list->
            Log.d("getAllTasks_finished", list.toString())
            taskAdapter = TaskAdapter(viewModel, list)
            // set the layout manager and the adapter for the recycler view
            binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.adapter = taskAdapter
        })

        return binding.root
    }
}
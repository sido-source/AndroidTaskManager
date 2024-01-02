package com.example.taskmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.DataSource
import com.example.taskmanager.Navigable
import com.example.taskmanager.adapter.TasksAdapter
import com.example.taskmanager.databinding.FragmentListBinding
import java.time.LocalDate
import java.time.temporal.WeekFields
import kotlin.concurrent.thread

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private var adapter: TasksAdapter? = null;
    private var lastTasksSize = 0;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListBinding.inflate(inflater, container, false).also {
            binding = it;
        }.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TasksAdapter()
        validateDeadlines()
        loadData()
        val currentWeek = LocalDate.now().get(WeekFields.ISO.weekOfWeekBasedYear()) // Get the current week number

        val objectsInCurrentWeek = DataSource.tasks.filter {
            val weekOfDeadline = it.deadline.get(WeekFields.ISO.weekOfWeekBasedYear())
            weekOfDeadline == currentWeek
        }

        binding.tvThisWeekTask.setText(objectsInCurrentWeek.size.toString())


        binding.listView.let {
            it.adapter = adapter
            //to be able to add items in linear way, second item is below first one
            it.layoutManager = LinearLayoutManager(requireContext())
        }

        // we want to "Add" button navigate us to "ADD" layout
        binding.buttonAdd.setOnClickListener {
            (activity as? Navigable)?.navigate(Navigable.Destination.Add, null)
        }

        binding.buttonSort.setOnClickListener {
            adapter?.sort()
        }

    }

    private fun validateDeadlines() {
        if (lastTasksSize != DataSource.tasks.size) {
            DataSource.tasks = DataSource.tasks.filter { task ->  task.deadline >= LocalDate.now()}.toMutableList()
            lastTasksSize = DataSource.tasks.size
        }
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() =  thread {

//        requireActivity().runOnUiThread {
            adapter?.replace(DataSource.tasks)
//        }
    }
}
package com.example.taskmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.adapter.TaskItemAdapter
import com.example.taskmanager.databinding.FragmentListBinding
import com.example.taskmanager.model.Priority
import com.example.taskmanager.model.Task
import java.time.LocalDate
import kotlin.concurrent.thread

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private var adapter: TaskItemAdapter? = null;

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

        adapter = TaskItemAdapter(createTasksList())

        binding.listView.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun createTasksList(): List<Task> {
        return listOf(
            Task(
                name = "Finish Kotlin Course",
                priority = Priority.HIGH,
                deadline = LocalDate.of(2023, 12, 31),
                percentageOfDone = 0.0,
                estimatedTimeInMinutes = 600
            ),
            Task(
                name = "Write Android App",
                priority = Priority.MEDIUM,
                deadline = LocalDate.of(2023, 6, 30),
                percentageOfDone = 0.0,
                estimatedTimeInMinutes = 4800
            ),
            Task(
                name = "Buy Groceries",
                priority = Priority.LOW,
                deadline = LocalDate.of(2023, 7, 10),
                percentageOfDone = 0.0,
                estimatedTimeInMinutes = 60
            )
        )
    }
}
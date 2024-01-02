package com.example.taskmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskmanager.DataSource
import com.example.taskmanager.Navigable
import com.example.taskmanager.databinding.FragmentEditCreateBinding
import com.example.taskmanager.model.Priority
import com.example.taskmanager.model.Task
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

const val ARG_EDIT_ID = "edit_id"
class EditFragment : Fragment(){

    private lateinit var binding: FragmentEditCreateBinding
    private var task: Task? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = requireArguments().getLong(ARG_EDIT_ID, -1L)

        if (id != -1L) {
            task = DataSource.tasks.find { it.id == id }
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEditCreateBinding.inflate(inflater, container, false).also {
            binding = it;
        }.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvName.setText(task?.name ?: "Not named yet")
        binding.tvDescription.setText(task?.description ?: "No description yet")
        binding.tvDate.setText(task?.deadline?.toString()
            ?: LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        binding.tvEstimatedTime.setText(task?.estimatedTimeInMinutes?.toString() ?: "0")
        binding.tvProgressPercentage.setText(task?.percentageOfDone?.toString() ?: "0")
        binding.tvPriority.setText(task?.priority ?: "LOW")

        // we want to "Add" button navigate us to "ADD" layout
        binding.btnBack.setOnClickListener(){
            (activity as? Navigable)?.navigate(Navigable.Destination.Add, null)
        }

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnSave.setOnClickListener {
            val dateText = binding.tvDate.text.toString()

            val newTask = Task(
                DataSource.tasks.get(DataSource.tasks.size-1).id + 1,
                binding.tvName.text.toString(),
                binding.tvPriority.text.toString(),
                LocalDate.parse(dateText),
                binding.tvProgressPercentage.text.toString().toDouble(),
                binding.tvEstimatedTime.text.toString().toInt(),
                binding.tvDescription.text.toString()
            )

            if (this.task?.id != -1L) {
                DataSource.tasks.removeIf { it.id == (task?.id ?: -1L) }
            }

            DataSource.tasks.add(newTask)

            (activity as? Navigable)?.navigate(Navigable.Destination.List, null)
        }
    }
}
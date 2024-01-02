package com.example.taskmanager.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskmanager.DataSource
import com.example.taskmanager.Navigable
import com.example.taskmanager.databinding.FragmentViewBinding
import com.example.taskmanager.model.Task
import com.example.taskmanager.util.CircleProgressDrawable
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ViewFragment : Fragment() {

    private lateinit var binding: FragmentViewBinding
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
        return FragmentViewBinding.inflate(inflater, container, false).also {
            binding = it;
        }.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvName.setText(task?.name ?: "Not named yet")
        binding.tvDescription.setText(task?.description ?: "No description yet")
        binding.tvDate.setText(task?.deadline?.toString() ?: LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
        binding.tvEstimatedTime.setText(task?.estimatedTimeInMinutes?.toString()?: "0")
        binding.tvProgressPercentage.setText(task?.percentageOfDone?.toString() ?: "0")
        binding.tvPriority.setText(task?.priority ?: "LOW")
        val progressDrawable = CircleProgressDrawable()

        // Set the progress percentage (assuming task is your Task object)
        task?.let {
            progressDrawable.setProgress(it.percentageOfDone)
        }

        // Set the drawable to the ImageView
        binding.iVFvProgressCircle.setImageDrawable(progressDrawable)

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnEdit.setOnClickListener {
            (activity as? Navigable)?.navigate(Navigable.Destination.Add, task?.id)
        }

        binding.btnSms.setOnClickListener {

            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, task.toString())
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}
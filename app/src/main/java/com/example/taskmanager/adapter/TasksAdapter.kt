package com.example.taskmanager.adapter

import android.app.AlertDialog
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.HandlerCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.DataSource
import com.example.taskmanager.Navigable
import com.example.taskmanager.databinding.RecyclerViewItemBinding
import com.example.taskmanager.fragments.ListFragment

import com.example.taskmanager.model.Task
import com.example.taskmanager.util.TasksCallback

// TaskItemAdapterViewHolder: a ViewHolder class for binding RecyclerView item layout to the data.
class TaskViewHolder(
    private val binding: RecyclerViewItemBinding,
    private val adapter: TasksAdapter // Reference to adapter
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var currentTask: Task

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this) // Set long-click listener
    }

    // Bind data to views
    fun bind(task: Task) {
        currentTask = task // Keep track of the current task for click handling
        binding.taskNameTV.text = task.name
        binding.tvDeadline.text = task.deadline.toString()
        binding.tvPriorityEnum.text = task.priority
        binding.tvPercentageOfDone.text = task.percentageOfDone.toString()
    }

    override fun onClick(view: View) {
        // Handle regular click (if needed)
        (view.context as? Navigable)?.navigate(Navigable.Destination.View, currentTask.id)
    }

    override fun onLongClick(v: View?): Boolean {
        // Show confirmation dialog for deletion
        val position = adapterPosition
        val taskToDelete = DataSource.tasks.getOrNull(position)

        taskToDelete?.let {
            // Show delete confirmation dialog
            AlertDialog.Builder(v?.context)
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Delete") { _, _ ->
                    // Perform deletion when confirmed
                    val taskId = currentTask.id // Assuming id is the identifier of the task
                    adapter.deleteItem(taskId)
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss() // Dismiss the dialog without performing deletion
                }
                .show()
        }

        return true // Indicate that the long-click event has been handled
    }
}

// TaskItemAdapter: a RecyclerView.Adapter class for handling RecyclerView's dataset and item views.
class TasksAdapter(
    private val recyclerView: RecyclerView,
    private val iRecyclerView: IRecyclerView
) : RecyclerView.Adapter<TaskViewHolder>() {
    private val handler: Handler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val data = mutableListOf<Task>()

    fun deleteItem(taskId: Long) {
        iRecyclerView.onHoldItem(taskId)
    }

    // onCreateViewHolder: a function to create a new RecyclerView.ViewHolder instance when RecyclerView needs to show an new item which wasn't on the screen earlier.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = RecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding, this)
    }

    // onBindViewHolder: a function to bind the task data to the ViewHolder's views when RecyclerView needs to show an item.
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = data[position]
        holder.bind(task)
    }

    // getItemCount: a function to return the total number of items in the data set.
    override fun getItemCount(): Int {
        return data.size
    }

    // Function to handle deletion of an item
    fun onDeleteItem(taskId: Long) {
        val position = data.indexOfFirst { it.id == taskId }
        if (position != -1) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun replace(newData: List<Task>) {
        val callback = TasksCallback(data, newData)
        data.clear()
        data.addAll(newData)
        val result = DiffUtil.calculateDiff(callback)
        handler.post {
            result.dispatchUpdatesTo(this)
        }
    }

    fun sort() {
        val notSorted = data.toList()
        data.sortBy { it.deadline }
        val callback = TasksCallback(notSorted, data)
        val result = DiffUtil.calculateDiff(callback)
        handler.post {
            result.dispatchUpdatesTo(this)
        }
    }
}

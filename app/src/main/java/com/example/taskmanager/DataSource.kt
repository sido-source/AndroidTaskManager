package com.example.taskmanager

import com.example.taskmanager.model.Priority
import com.example.taskmanager.model.Task
import java.time.LocalDate

object DataSource {
    var tasks = mutableListOf<Task>(
        Task(
            id = 1L,
            name = "Finish Kotlin Course",
            priority = Priority.HIGH.toString(),
            deadline = LocalDate.of(2023, 12, 31),
            percentageOfDone = 10.0,
            estimatedTimeInMinutes = 600,
            description = "description1"
        ),
        Task(
            id = 2L,
            name = "Write Android App",
            priority = Priority.MEDIUM.toString(),
            deadline = LocalDate.of(2024, 1, 3),
            percentageOfDone = 0.0,
            estimatedTimeInMinutes = 4800,
            description = "description2"
        ),
        Task(
            id = 3L,
            name = "Buy Groceries",
            priority = Priority.LOW.toString(),
            deadline = LocalDate.of(2024, 7, 10),
            percentageOfDone = 25.0,
            estimatedTimeInMinutes = 60,
            description = "description3"
        ),
        Task(
            id = 4L,
            name = "Learn new language",
            priority = Priority.LOW.toString(),
            deadline = LocalDate.of(2024, 7, 9),
            percentageOfDone = 75.0,
            estimatedTimeInMinutes = 60,
            description = "description4"
        ),
        Task(
            id = 5L,
            name = "Create new project on the github",
            priority = Priority.HIGH.toString(),
            deadline = LocalDate.of(2024, 7, 23),
            percentageOfDone = 40.0,
            estimatedTimeInMinutes = 60,
            description = "description5"
        )
    )
}
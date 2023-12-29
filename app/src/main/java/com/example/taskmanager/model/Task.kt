package com.example.taskmanager.model

import java.time.LocalDate

enum class Priority {
    LOW, MEDIUM, HIGH
}
data class Task(
    var name: String,
    var priority: Priority,
    var deadline: LocalDate,
    var percentageOfDone: Double,
    var estimatedTimeInMinutes: Int
)


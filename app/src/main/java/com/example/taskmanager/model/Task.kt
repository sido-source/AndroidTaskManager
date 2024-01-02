package com.example.taskmanager.model

import java.time.LocalDate

enum class Priority {
    LOW, MEDIUM, HIGH
}
data class Task(
    val id: Long,
    var name: String,
    var priority: String,
    var deadline: LocalDate,
    var percentageOfDone: Double,
    var estimatedTimeInMinutes: Int,
    var description: String
) {

    override fun toString(): String {
        return "Details about the task (\n" +
                "name='$name',\n" +
                "priority='$priority',\n" +
                "deadline=$deadline,\n" +
                "percentageOfDone=$percentageOfDone,\n" +
                "estimatedTimeInMinutes=$estimatedTimeInMinutes,\n" +
                "description='$description'\n" +
                ")"
    }
}


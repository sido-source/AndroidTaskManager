package com.example.taskmanager

import com.example.taskmanager.model.Task

interface Navigable {
    enum class Destination{
        List, Add, View
    }

    fun navigate(to: Destination, taskId: Long?)
}
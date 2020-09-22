package com.memory.memory_kotlin

import java.util.*

data class TasksFirebase (
    val learnContext : String = "",
    val learn_details : String = "",
    val login_user : String = "",
    val remember_date : String = "",
    val created_at: Date = Date(),
    val update_at: Date = Date()
    )


package com.example.stms.database

import androidx.room.Entity
import androidx.room.PrimaryKey

// dayOfWeek: 1=Mon ... 7=Sun; store minutes since midnight for easy sort
@Entity(tableName = "routines")
data class Routine(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val className: String,        // student schedule key
    val teacherId: String? = null,// to filter for teacher view
    val dayOfWeek: Int,           // 1..7
    val startMinutes: Int,        // 9:30 -> 570
    val endMinutes: Int,          // 10:20 -> 620
    val subject: String,
    val room: String? = null,
    val courseDetails: String? = null
)
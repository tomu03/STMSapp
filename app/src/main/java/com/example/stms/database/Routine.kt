package com.example.stms.database

import androidx.room.Entity
import androidx.room.PrimaryKey

// dayOfWeek: 1=Mon ... 7=Sun; store minutes since midnight for easy sort
@Entity(tableName = "routines")
data class Routine(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val className: String,
    val teacherId: String?,  // teacher who owns it
    val dayOfWeek: Int,      // 1=Mon ... 7=Sun
    val startMinutes: Int,
    val endMinutes: Int,
    val subject: String,
    val room: String?,
    val courseDetails: String?
)
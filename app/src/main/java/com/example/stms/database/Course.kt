package com.example.stms.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val code: String,             // e.g., "MTH101"
    val title: String,            // e.g., "Mathematics I"
    val description: String? = null,
    val teacherId: String? = null,
    val className: String? = null
)
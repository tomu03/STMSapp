package com.example.stms

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val pk: Int = 0,
    val userId: String,          // student ID (shown in profile)
    val name: String,
    val email: String,
    val password: String,        // demo only; hash in real apps
    val className: String,
    val result: String?,
    val note: String?,
    val details: String?,
    val imageUri: String? = null // optional
)
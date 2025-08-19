package com.example.stms.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val pk: Int = 0,
    val userId: String,      // visible ID
    val name: String,
    val email: String,
    val password: String,    // demo only
    val className: String,   // e.g., "10A"
    val details: String? = null,
    val imageUri: String? = null
)
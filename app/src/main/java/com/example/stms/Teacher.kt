package com.example.stms

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teachers")
data class Teacher(
    @PrimaryKey(autoGenerate = true) val pk: Int = 0,
    val userId: String,          // teacher ID (shown in profile)
    val name: String,
    val email: String,
    val password: String,
    val className: String,
    val course: String,
    val result: String?,
    val details: String?,
    val imageUri: String? = null
)


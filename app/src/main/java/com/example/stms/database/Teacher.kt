package com.example.stms.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teachers")
data class Teacher(
    @PrimaryKey(autoGenerate = true) val pk: Int = 0,
    val userId: String,
    val name: String,
    val email: String,
    val password: String,
    val className: String,   // homeroom / main class
    val course: String,      // main course taught
    val details: String? = null,
    val imageUri: String? = null
)
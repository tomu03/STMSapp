package com.example.stms.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stms.database.Course
import com.example.stms.database.Routine

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: Course): Long

    @Query("SELECT * FROM courses WHERE teacherId = :teacherId")
    suspend fun getByTeacher(teacherId: String): List<Course>

    @Query("SELECT * FROM courses WHERE className = :cls")
    suspend fun getByClass(cls: String): List<Course>
}
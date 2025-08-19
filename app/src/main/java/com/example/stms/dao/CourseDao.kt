package com.example.stms.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stms.database.Course
import com.example.stms.database.Routine

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(course: Course)
    @Query("SELECT * FROM courses WHERE className=:className")
    suspend fun byClass(className: String): List<Course>
    @Query("SELECT * FROM courses WHERE teacherId=:teacherId")
    suspend fun byTeacher(teacherId: String): List<Course>
}
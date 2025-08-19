package com.example.stms.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stms.dao.CourseDao
import com.example.stms.dao.RoutineDao
import com.example.stms.database.Student
import com.example.stms.database.Teacher
import com.example.stms.dao.StudentDao
import com.example.stms.dao.TeacherDao

@Database(
    entities = [Student::class, Teacher::class, Routine::class, Course::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun teacherDao(): TeacherDao
    abstract fun routineDao(): RoutineDao
    abstract fun courseDao(): CourseDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "stms_db"
                )
                    // Dev only – wipes on schema change. Replace with real migrations later.
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
    }
}

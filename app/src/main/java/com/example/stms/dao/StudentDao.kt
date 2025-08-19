package com.example.stms.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stms.database.Student

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.Companion.ABORT) suspend fun insert(student: Student)
    @Query("SELECT * FROM students WHERE email=:email AND password=:password LIMIT 1")
    suspend fun login(email: String, password: String): Student?
    @Query("SELECT * FROM students WHERE pk=:pk LIMIT 1")
    suspend fun getByPk(pk: Int): Student?
}
package com.example.stms

import androidx.room.*

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(student: Student)

    @Query("SELECT * FROM students WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): Student?

    @Query("SELECT * FROM students WHERE pk = :pk LIMIT 1")
    suspend fun getByPk(pk: Int): Student?
}

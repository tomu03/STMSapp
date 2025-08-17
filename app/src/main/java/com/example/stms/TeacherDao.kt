package com.example.stms

import androidx.room.*

@Dao
interface TeacherDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(teacher: Teacher)

    @Query("SELECT * FROM teachers WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): Teacher?

    @Query("SELECT * FROM teachers WHERE pk = :pk LIMIT 1")
    suspend fun getByPk(pk: Int): Teacher?
}

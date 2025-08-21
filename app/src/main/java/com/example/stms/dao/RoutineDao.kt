package com.example.stms.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stms.database.Routine

@Dao
interface RoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(routine: Routine)

    @Query("SELECT * FROM routines WHERE className = :cls AND dayOfWeek = :day ORDER BY startMinutes ASC")
    suspend fun getRoutineForDay(cls: String, day: Int): List<Routine>

    @Query("SELECT * FROM routines WHERE className = :cls ORDER BY dayOfWeek, startMinutes")
    suspend fun forClass(cls: String): List<Routine>

    @Query("SELECT * FROM routines WHERE className = :cls ORDER BY dayOfWeek, startMinutes")
    suspend fun forTeacher(cls: String): List<Routine>

    @Query("SELECT * FROM routines ORDER BY className, dayOfWeek, startMinutes")
    suspend fun allRoutines(): List<Routine>

    @Query("UPDATE routines SET subject = :subject, dayOfWeek = :dayOfWeek, startMinutes = :startMinutes, endMinutes = :endMinutes, room = :room, courseDetails = :details WHERE id = :id")
    suspend fun updateRoutine(id: Int, subject: String, dayOfWeek: Int, startMinutes: Int, endMinutes: Int, room: String?, details: String?)


}
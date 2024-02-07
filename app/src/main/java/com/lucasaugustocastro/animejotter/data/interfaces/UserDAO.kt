package com.lucasaugustocastro.animejotter.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.lucasaugustocastro.animejotter.entities.User

@Dao
interface UserDAO {
    @Insert
    fun insert(user: User): Long

    @Update
    fun update(user: User): Int

    @Delete
    fun delete(user: User): Int

    @Query("SELECT * FROM user WHERE id = :id")
    fun findById(id: Long): User

    @Query("SELECT * FROM user WHERE email = :email")
    fun findByEmail(email: String): User

}
package com.lucasaugustocastro.animejotter.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.lucasaugustocastro.animejotter.entities.WatchedTitle

@Dao
interface WatchedTitleDao {
    @Insert
    fun insert(anime: WatchedTitle): Long

    @Update
    fun update(anime: WatchedTitle): Int

    @Delete
    fun delete(anime: WatchedTitle): Int

    @Query("SELECT * FROM watched_title WHERE id = :id")
    fun findById(id: Int): WatchedTitle

    @Query("SELECT * FROM watched_title WHERE customer_id = :customerId")
    fun findAllByCustomer(customerId: Long): List<WatchedTitle>

    @Query("SELECT * FROM watched_title WHERE customer_id = :customerId AND favorite = :favorite")
    fun findByFavorite(customerId: Long, favorite:Boolean): List<WatchedTitle>
}
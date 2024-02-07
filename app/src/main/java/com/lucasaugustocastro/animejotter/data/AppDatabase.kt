package com.lucasaugustocastro.animejotter.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lucasaugustocastro.animejotter.entities.User
import com.lucasaugustocastro.animejotter.data.interfaces.WatchedTitleDao
import com.lucasaugustocastro.animejotter.entities.WatchedTitle
import com.lucasaugustocastro.animejotter.data.interfaces.UserDAO

@Database(
    entities = [WatchedTitle::class, User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun watchedTitleDao(): WatchedTitleDao
    abstract fun userDao(): UserDAO

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            return this.instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "animejotter_database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                this.instance = instance
                return instance
            }
        }
    }
}
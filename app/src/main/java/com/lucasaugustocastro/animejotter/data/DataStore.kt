package com.lucasaugustocastro.animejotter.data

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.lucasaugustocastro.animejotter.entities.User
import com.lucasaugustocastro.animejotter.data.interfaces.WatchedTitleDao
import com.lucasaugustocastro.animejotter.entities.WatchedTitle
import com.lucasaugustocastro.animejotter.data.interfaces.UserDAO

object DataStore {
    var watchedTitles: MutableList<WatchedTitle> = arrayListOf()
    var favoriteTitles: MutableList<WatchedTitle> = arrayListOf()
    private var database: AppDatabase? = null
    private var watchedTitleDao: WatchedTitleDao? = null
    private var userDao: UserDAO? = null

    fun setContext(context: Context) {
        database = AppDatabase.getInstance(context)

        database?.let { db ->
            watchedTitleDao = db.watchedTitleDao()
            userDao = db.userDao()
        }
    }

    fun getWatchedTitles(customerId: Long){
        watchedTitleDao?.let { db ->
            val allWatchedTitles = db.findAllByCustomer(customerId)
            watchedTitles.clear()
            watchedTitles.addAll(allWatchedTitles)

        }
    }
    fun getWatchedTitle(position: Int):WatchedTitle {
        return watchedTitles[position]
    }
    fun saveWatchedTitle(title: WatchedTitle): Long {
        watchedTitleDao?.let { db ->
            val id = db.insert(title)
            if (id > 0) {
                title.id = id
                watchedTitles.add(title)
            } else {
                Log.d("AnimeJotter", "Erro ao salvar dados no banco de dados")
            }
            return id
        }
        return -1
    }
    fun deleteWatchedTitle(watchedTitle: WatchedTitle) {

        watchedTitleDao?.let { db ->

            db.delete(watchedTitle)
            watchedTitles.remove(watchedTitle)
            favoriteTitles.remove(watchedTitle)
        }
    }

    fun getFavoriteTitles(customerId: Long){

        watchedTitleDao?.let { db ->
            val allFavoriteTitles = db.findByFavorite(customerId, favorite = true)
            favoriteTitles.clear()
            favoriteTitles.addAll(allFavoriteTitles)

        }
    }

    fun setFavorite(position:Int, fromFavorite: Boolean){
        val item = if (fromFavorite) favoriteTitles[position] else  watchedTitles[position]
        item.favorite = !item.favorite
        watchedTitleDao?.let { db ->
            db.update(item)
        }
    }


    fun getCustomerById(id: Long): User?{
        userDao?.let { db ->
            return db.findById(id)
        }
        return null
    }
    fun getCustomerByEmail(email: String): User?{
        userDao?.let { db ->
            return db.findByEmail(email)
        }
        return null
    }
    fun saveCustomer(user:User): Long{
        try {
            userDao?.let { db ->
                return db.insert(user)
            }
            return -1
        } catch (ex: SQLiteConstraintException){
            throw ex
        }

    }
    fun updateCustomer(user:User): Long{
        userDao?.let { db ->
            return db.update(user).toLong()
        }
        return -1
    }
}
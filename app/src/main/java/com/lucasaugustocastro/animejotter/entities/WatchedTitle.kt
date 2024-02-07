package com.lucasaugustocastro.animejotter.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "watched_title",
    foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["customer_id"])])
class WatchedTitle(

    @ColumnInfo(name = "title_id")
    var title_id: Long,
    @ColumnInfo(name = "title_image")
    var title_image: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "author")
    var author: String,
    @ColumnInfo(name = "release_at")
    var release_at: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "favorite")
    var favorite: Boolean,
    @ColumnInfo(name = "customer_id", index = true)
    val customer_id: Long,

) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}
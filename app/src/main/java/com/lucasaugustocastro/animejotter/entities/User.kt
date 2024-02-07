package com.lucasaugustocastro.animejotter.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user", indices = [Index(value = ["email"], unique = true)])
class User(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "password")
    var password: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}
package com.example.mygithubuserapp3.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class User(
    @PrimaryKey
    @ColumnInfo
    val username: String,

    @ColumnInfo
    val avatar: String,

    @ColumnInfo
    var isFavorite: Boolean
) : Parcelable
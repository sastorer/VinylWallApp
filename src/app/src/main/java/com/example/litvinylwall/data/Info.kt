package com.example.litvinylwall.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "info_table")
data class Info(
    @PrimaryKey
    var id: Int = 0,

    @ColumnInfo(name = "imageURL")
    val imgURL: String = "",

    @ColumnInfo(name = "soundURI")
    var soundURI: String = "",

    @ColumnInfo(name = "imgTag")
    var imgTag: String = ""
)
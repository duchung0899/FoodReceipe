package com.hungngo.foodreceipeappkotlin.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "recipe")
data class Recipe (
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "dishName")
    var dishName:String
): Serializable
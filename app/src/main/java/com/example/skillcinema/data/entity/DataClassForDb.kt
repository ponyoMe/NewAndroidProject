package com.example.skillcinema.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_class_db")
data class DataClassForDb(
  @PrimaryKey val movieId : Int,
    val posterurl : String?,
    val name: String,
    val rating : Double,
    val genre : String,
    val category :String
)

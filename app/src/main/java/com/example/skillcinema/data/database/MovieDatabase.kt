package com.example.skillcinema.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.skillcinema.data.dao.MovieDao
import com.example.skillcinema.data.entity.DataClassForDb

@Database(entities = [DataClassForDb::class], version = 2, exportSchema = true)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun dao() : MovieDao
}

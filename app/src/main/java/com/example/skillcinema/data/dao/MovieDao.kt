package com.example.skillcinema.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.skillcinema.data.entity.DataClassForDb

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie : DataClassForDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(movie : List<DataClassForDb>)//kinda dont need it

    @Query("DELETE FROM data_class_db")
    suspend fun deleteList()

    @Query("delete from data_class_db where data_class_db.movieId = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM data_class_db WHERE movieId = :id")
    suspend fun getMovieById(id: Int): DataClassForDb?

    @Query("SELECT * FROM data_class_db")
    suspend fun getAllMovie() : List<DataClassForDb>

    @Query("select count(*) from data_class_db where category = 'fav' ")
    suspend fun getFavMovies() :Int

    @Query("select count(*) from data_class_db where category = 'saved' ")
    suspend fun getSavedMovies() :Int

}
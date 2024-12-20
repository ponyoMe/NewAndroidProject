package com.example.skillcinema.domain.model

import android.health.connect.datatypes.units.Length
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skillcinema.data.model.Country
import com.example.skillcinema.data.model.Genre
import kotlinx.serialization.Serializable


@Serializable

data class Movie(
    val kinopoiskId: Int,
    val imdbId: String?,
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String?,
    val countries: List<Country>?,
    val genres: List<Genre>?,
    val ratingKinopoisk: Double?,
    val ratingImdb: Double?,
    val year: Int?,
    val filmLength: Int?,
    val description: String?,
    val shortDescription: String?,
    val ratingAgeLimits: String?,
    val type: String?,
    val coverUrl: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val fav : Boolean = false,
    val wanna : Boolean = false
)
package com.example.skillcinema.domain.model

import android.health.connect.datatypes.units.Length
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
    val countries: List<Country>,
    val genres: List<Genre>,
    val ratingKinopoisk: Double?,
    val ratingImdb: Double?,
    val year: Int?,
    val filmLength: String,
    val description: String,
    val shortDescription: String,
    val ratingAgeLimits: String?,
    val type: String?,
    val coverUrl: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?
)
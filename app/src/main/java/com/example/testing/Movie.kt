package com.example.testing

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class MovieResponse(
    val items: List<Movie>,
    val total: Int,
    val totalPages: Int
)

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
    val type: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?
)

@Serializable
data class Country(
    val country: String
)

@Serializable
data class Genre(
    val genre: String
)
package com.example.skillcinema.data.model

import com.example.skillcinema.domain.model.Movie

data class MovieResponse(
    val items: List<Movie>,
    val total: Int,
    val totalPages: Int
)

data class MovieSearchResponse(
    val films: List<MovieSearch>,
    val searchFilmsCountResult: Int,
    val pagesCount: Int
)

data class MovieSearch(
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val type: String,
    val year: String,
    val description: String,
    val filmLength: String,
    val countries: List<Country>,
    val genres: List<Genre>,
    val rating: String,
    val ratingVoteCount: Int,
    val posterUrl: String,
    val posterUrlPreview: String
)
package com.example.skillcinema.data.model

data class SimilarMovies (
    var total: Int,
    val items: List<SimilarMovie>
)

data class SimilarMovie(
    val filmId: Int,
    val nameRu: String,
    val nameEn: String,
    val nameOriginal: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val relationType: String
)
package com.example.skillcinema.data.model

data class MovieResponse(
    val items: List<Movie>,
    val total: Int,
    val totalPages: Int
)
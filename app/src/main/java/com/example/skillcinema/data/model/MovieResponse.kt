package com.example.skillcinema.data.model

import com.example.skillcinema.domain.model.Movie

data class MovieResponse(
    val items: List<Movie>,
    val total: Int,
    val totalPages: Int
)
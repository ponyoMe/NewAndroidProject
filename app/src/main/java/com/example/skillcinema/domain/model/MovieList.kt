package com.example.skillcinema.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieList (
    val films: List<Movie>
)
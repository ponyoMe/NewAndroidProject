package com.example.skillcinema.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FilmImagesResponse(
    val total: Int,
    val totalPages: Int,
    val items: List<FilmImage>
)

@Serializable
data class FilmImage(
    val imageUrl: String,
    val previewUrl: String
)
package com.example.skillcinema.data.model




data class Film(
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val rating: String?,
    val general: Boolean,
    val description: String?,
    val professionKey: String
)
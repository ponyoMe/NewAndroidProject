package com.example.skillcinema.presentation.film

import com.example.skillcinema.data.model.FilmImage
import com.example.skillcinema.data.model.StaffResponse

sealed class ImagesState {
    data object Loading: ImagesState()
    data class Success(val images: List<FilmImage>) : ImagesState()
    data class Error(val message: String) : ImagesState()
}
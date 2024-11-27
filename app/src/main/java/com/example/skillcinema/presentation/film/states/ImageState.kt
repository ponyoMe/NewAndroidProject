package com.example.skillcinema.presentation.film.states

import com.example.skillcinema.data.model.FilmImage

sealed class ImagesState {
    data object Loading: ImagesState()
    data class Success(val images: List<FilmImage>) : ImagesState()
    data class Error(val message: String) : ImagesState()
}
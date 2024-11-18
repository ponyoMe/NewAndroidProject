package com.example.skillcinema.presentation.film

import com.example.skillcinema.data.model.FilmImage
import com.example.skillcinema.data.model.StaffResponse
import com.example.skillcinema.domain.model.Movie

sealed class FilmState {
    data object Loading: FilmState()
    data class Success(val movie: Movie) : FilmState()
    data class Error(val message: String) : FilmState()
}

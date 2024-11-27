package com.example.skillcinema.presentation.film.states

import com.example.skillcinema.data.model.SimilarMovie

sealed class SimilarFilmState {
    data object Loading: SimilarFilmState()
    data class Success(val similarMovies: List<SimilarMovie>) : SimilarFilmState()
    data class Error(val message: String) : SimilarFilmState()
}

package com.example.skillcinema.presentation.search

import com.example.skillcinema.data.model.MovieSearch
import com.example.skillcinema.domain.model.Movie
import com.example.skillcinema.domain.model.MovieList

sealed class SearchState {
    data object Loading: SearchState()
    data class Success(val movies: List<MovieSearch>) : SearchState()
    data class Error(val message: String) : SearchState()
    data object Empty : SearchState()
}
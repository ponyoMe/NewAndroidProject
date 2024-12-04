package com.example.skillcinema.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.repository.MovieRepository
import com.example.skillcinema.domain.model.Movie
import com.example.skillcinema.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MovieScreenState {
    data object Initial : MovieScreenState()
    data object Loading : MovieScreenState()
    data class Success(
        var popularMovies: List<Movie> = emptyList(),
        val topRatedMovies: List<Movie> = emptyList(),
        val nowShowingMovies: List<Movie> = emptyList(),
        val errors: Map<String, String> = emptyMap()
    ) : MovieScreenState()
}

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MovieScreenState>(MovieScreenState.Initial)
    val state: StateFlow<MovieScreenState> = _state.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _state.value = MovieScreenState.Loading

            val errors = mutableMapOf<String, String>()
            var popularMovies = emptyList<Movie>()
            var topRatedMovies = emptyList<Movie>()
            var nowShowingMovies = emptyList<Movie>()

            movieUseCase.getPopularMovies().onSuccess { movieList ->
                popularMovies = movieList.films
                //repository.insertList(popularMovies)
            }.onFailure { e ->
                errors["popular"] = e.message ?: "Failed to fetch popular movies"
            }

            movieUseCase.getTopRatedMovies().onSuccess { movieList ->
                topRatedMovies = movieList.films
            }.onFailure { e ->
                errors["topRated"] = e.message ?: "Failed to fetch top rated movies"
            }

            movieUseCase.getNowShowingMovies().onSuccess { movieList ->
                nowShowingMovies = movieList.films
            }.onFailure { e ->
                errors["nowShowing"] = e.message ?: "Failed to fetch now showing movies"
            }

            _state.value = MovieScreenState.Success(
                popularMovies = popularMovies,
                topRatedMovies = topRatedMovies,
                nowShowingMovies = nowShowingMovies,
                errors = errors
            )
        }
    }



    fun retry() {
        loadMovies()
    }
}
package com.example.skillcinema.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.model.Movie
import com.example.skillcinema.domain.usecase.GetMovies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class MovieScreenState {
    data object Initial : MovieScreenState()
    data object Loading : MovieScreenState()
    data class Success(
        val popularMovies: List<Movie> = emptyList(),
        val topRatedMovies: List<Movie> = emptyList(),
        val nowShowingMovies: List<Movie> = emptyList(),
        val errors: Map<String, String> = emptyMap()
    ) : MovieScreenState()
}

class MovieViewModel(private val getMovies: GetMovies) : ViewModel() {
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
            var topRatedMovies= emptyList<Movie>()
            var nowShowingMovies= emptyList<Movie>()

            getMovies.getPopularMovies().onSuccess { movieList ->
                popularMovies = movieList.films
            }.onFailure { e ->
                errors["popular"] = e.message ?: "Failed to fetch popular movies"
            }

            getMovies.getTopRatedMovies().onSuccess { movieList ->
                topRatedMovies = movieList.films
            }.onFailure { e ->
                errors["topRated"] = e.message ?: "Failed to fetch top rated movies"
            }

            getMovies.getNowShowingMovies().onSuccess { movieList ->
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

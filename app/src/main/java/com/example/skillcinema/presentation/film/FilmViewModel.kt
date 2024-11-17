package com.example.skillcinema.presentation.film

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.model.FilmImage
import com.example.skillcinema.data.model.FilmImagesResponse
import com.example.skillcinema.domain.model.Movie
import com.example.skillcinema.domain.usecase.MovieUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class FilmState {
    data object Loading: FilmState()
    data class Success(val movie: Movie, val images: List<FilmImage>) : FilmState()
    data class Error(val movie: String) : FilmState()
}

@HiltViewModel(assistedFactory = FilmViewModel.ViewModelFactory::class)
class FilmViewModel @AssistedInject constructor(
    private val movieUseCase: MovieUseCase,
    @Assisted private val filmId: Int,
): ViewModel() {
    private val _filmState = MutableStateFlow<FilmState>(FilmState.Loading)
    val filmState: StateFlow<FilmState> get() = _filmState

    init {
        fetchFilmById(filmId)
    }

    fun fetchFilmById(id: Int) = viewModelScope.launch {
        _filmState.value = FilmState.Loading

        movieUseCase.getFilmById(id)
            .onSuccess { film ->
                Log.d("FilmViewModel", "Film loaded, ${film.kinopoiskId}")
                _filmState.value = FilmState.Success(movie = film, images = emptyList())
                fetchImagesByFilmId(id)
            }
            .onFailure { error ->
                _filmState.value = FilmState.Error("error loading film: ${error.message}")
            }
    }

    fun fetchImagesByFilmId(id: Int) = viewModelScope.launch{
        val result = movieUseCase.getImagesByFilmId(id)
        Log.d("FilmViewModel", "Loading images, Film ID: $id")
        result.onSuccess { filmImagesResponse ->
            Log.d("FilmViewModel", "images loaded, ${filmImagesResponse.items.size} items")
            val currentFilmState = filmState.value
            if (currentFilmState is FilmState.Success){
                Log.d("FilmViewModel", "Checking")
                _filmState.value = FilmState.Success(
                    movie = currentFilmState.movie,
                    images = filmImagesResponse.items
                )
            } else{
                Log.d("FilmViewModel", "Current state is not FilmState.Success")
            }
        }.onFailure { error ->
            Log.d("FilmViewModel", "images error items")
            _filmState.value = FilmState.Error("error loading film images: ${error.message}")
        }
    }

    @AssistedFactory
    interface ViewModelFactory {
        fun create(filmId: Int): FilmViewModel
    }
}

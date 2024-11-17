package com.example.skillcinema.presentation.film

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    data class Success(val movie: Movie) : FilmState()
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
                _filmState.value = FilmState.Success(film)
            }
            .onFailure { error ->
                _filmState.value = FilmState.Error("error loading film: ${error.message}")
            }
    }

    fun fetchImagesByFilmId(id: Int){

    }

    @AssistedFactory
    interface ViewModelFactory {
        fun create(filmId: Int): FilmViewModel
    }
}

package com.example.skillcinema.presentation.film

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillcinema.data.model.FilmImage
import com.example.skillcinema.data.model.FilmImagesResponse
import com.example.skillcinema.data.model.StaffResponse
import com.example.skillcinema.domain.model.Movie
import com.example.skillcinema.domain.usecase.MovieUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



@HiltViewModel(assistedFactory = FilmViewModel.ViewModelFactory::class)
class FilmViewModel @AssistedInject constructor(
    private val movieUseCase: MovieUseCase,
    @Assisted private val filmId: Int,
): ViewModel() {
    private val _filmState = MutableStateFlow<FilmState>(FilmState.Loading)
    val filmState: StateFlow<FilmState> get() = _filmState

    private val _staffState = MutableStateFlow<StaffState>(StaffState.Loading)
    val staffState: StateFlow<StaffState> get() = _staffState

    private val _imagesState = MutableStateFlow<ImagesState>(ImagesState.Loading)
    val imagesState: StateFlow<ImagesState> get() = _imagesState

    init{
        fetchFilmById(filmId)
        fetchStaffByFilmId(filmId)
        fetchImagesByFilmId(filmId)
    }

    fun fetchFilmById(id: Int) = viewModelScope.launch {
        _filmState.value = FilmState.Loading
        movieUseCase.getFilmById(id)
            .onSuccess { film ->
                Log.d("FilmViewModel", "Film loaded: ${film.kinopoiskId}")
                _filmState.value = FilmState.Success(movie = film)
            }
            .onFailure { error ->
                Log.d("FilmViewModel", "error")
                _filmState.value = FilmState.Error(message = "Error loading film: ${error.message}")
            }
    }

    fun fetchStaffByFilmId(id: Int) = viewModelScope.launch {
        _staffState.value = StaffState.Loading
        movieUseCase.getStaffByFilmId(id)
            .onSuccess { staffList ->
                Log.d("FilmViewModel", "Staff loaded: ${staffList.size}")
                _staffState.value = StaffState.Success(staff = staffList)
            }
            .onFailure { error ->
                Log.d("FilmViewModel", "error")
                _staffState.value = StaffState.Error(message = "Error loading staff: ${error.message}")
            }
    }

    fun fetchImagesByFilmId(id: Int) = viewModelScope.launch {
        _imagesState.value = ImagesState.Loading
        movieUseCase.getImagesByFilmId(id)
            .onSuccess { filmImagesResponse ->
                Log.d("FilmViewModel", "Images loaded: ${filmImagesResponse.items.size}")
                _imagesState.value = ImagesState.Success(images = filmImagesResponse.items)
            }
            .onFailure { error ->
                Log.d("FilmViewModel", "error")
                _imagesState.value = ImagesState.Error(message = "Error loading film: ${error.message}")
            }
    }

    @AssistedFactory
    interface ViewModelFactory {
        fun create(filmId: Int): FilmViewModel
    }
}

package com.example.skillcinema.presentation.film

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.data.repository.MovieRepository
import com.example.skillcinema.domain.model.Movie
import com.example.skillcinema.domain.usecase.MovieUseCase
import com.example.skillcinema.presentation.film.states.FilmState
import com.example.skillcinema.presentation.film.states.ImagesState
import com.example.skillcinema.presentation.film.states.SimilarFilmState
import com.example.skillcinema.presentation.film.states.StaffState
import com.example.skillcinema.data.entity.DataClassForDb
import com.example.skillcinema.data.dao.MovieDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = FilmViewModel.ViewModelFactory::class)
class FilmViewModel @AssistedInject constructor(
    private val movieUseCase: MovieUseCase,
    @Assisted private val filmId: Int,
    private val repository: MovieRepository,
    private val dao : MovieDao
): ViewModel() {

    private val _filmState = MutableStateFlow<FilmState>(FilmState.Loading)
    val filmState: StateFlow<FilmState> get() = _filmState

    private val _staffState = MutableStateFlow<StaffState>(StaffState.Loading)
    val staffState: StateFlow<StaffState> get() = _staffState

    private val _imagesState = MutableStateFlow<ImagesState>(ImagesState.Loading)
    val imagesState: StateFlow<ImagesState> get() = _imagesState

    private val _similarFilmsState = MutableStateFlow<SimilarFilmState>(SimilarFilmState.Loading)
    val similarMoviesState : StateFlow<SimilarFilmState> get() = _similarFilmsState

    private val _hasWatched = MutableStateFlow(false)
    val hasWatched :StateFlow<Boolean> = _hasWatched

    private val _fav = MutableStateFlow(false)
    val fav :StateFlow<Boolean> = _fav

    private val _saved  = MutableStateFlow(false)
    val saved :StateFlow<Boolean> = _saved

    init{
        fetchFilmById(filmId)
        fetchStaffByFilmId(filmId)
        fetchImagesByFilmId(filmId)
        fetchSimilarMovies(filmId)
        forInitDatabase(filmId)
    }

    fun fetchFilmById(id: Int) = viewModelScope.launch {
        _filmState.value = FilmState.Loading
        movieUseCase.getFilmById(id)
            .onSuccess { film ->
                //Log.d("FilmViewModel", "Film loaded: ${film.kinopoiskId}")
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
                //Log.d("FilmViewModel", "Staff loaded: ${staffList.size}")
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
                //Log.d("FilmViewModel", "Images loaded: ${filmImagesResponse.items.size}")
                _imagesState.value = ImagesState.Success(images = filmImagesResponse.items)
            }
            .onFailure { error ->
                Log.d("FilmViewModel", "error")
                _imagesState.value = ImagesState.Error(message = "Error loading film: ${error.message}")
            }
    }

    fun fetchSimilarMovies(id: Int) = viewModelScope.launch {
        _similarFilmsState.value = SimilarFilmState.Loading

        try {
            val similarFilm = movieUseCase.getSimilarMovies(id)
            //Log.d("FilmViewModel", "Images loaded: ${similarFilm.items.size}")
            _similarFilmsState.value = SimilarFilmState.Success(similarMovies = similarFilm.items)
        } catch (error: Exception){
            _similarFilmsState.value = error.message?.let { SimilarFilmState.Error(message = it) }!!
        }

    }

    fun forInitDatabase(id : Int){
        viewModelScope.launch {
            val movie = dao.getMovieById(id)
            _fav.value = movie?.category == "fav"
            _saved.value = movie?.category =="saved"
            _hasWatched.value = movie?.category == "watched"
        }

    }

    fun insertToDb(movie : Movie){
        viewModelScope.launch {
            var watched = DataClassForDb(
                movieId = movie.kinopoiskId,
                posterurl = movie.posterUrl,
                name = movie.nameRu ?: movie.nameEn ?: "",
                rating = movie.ratingKinopoisk ?: movie.ratingImdb ?: 7.0,
                genre = movie.genres.toString(),
                category = "watched"

            )
            dao.insert(watched)
            _hasWatched.value = true
        }
    }

    fun favMovies(movie: Movie){
        viewModelScope.launch {
            var fav = DataClassForDb(
                movieId = movie.kinopoiskId,
                posterurl = movie.posterUrl,
                name = movie.nameRu ?: movie.nameEn ?: "",
                rating = movie.ratingImdb?:movie.ratingKinopoisk?: 0.0,
                genre = movie.genres.toString(),
                category = "fav"
            )
            dao.insert(fav)
            _fav.value = true
        }
    }

    fun saved(movie: Movie){
        viewModelScope.launch {
            var saved = DataClassForDb(
                movieId = movie.kinopoiskId,
                posterurl = movie.posterUrl,
                name = movie.nameRu ?: movie.nameEn ?: "",
                rating = movie.ratingImdb?:movie.ratingKinopoisk?: 0.0,
                genre = movie.genres.toString(),
                category = "saved"
            )
            dao.insert(saved)
            _saved.value = true
        }
    }

    fun deleteWatched(id : Int){
        viewModelScope.launch {
            dao.deleteById(id)
            _hasWatched.value = false
        }
    }



    @AssistedFactory
    interface ViewModelFactory {
        fun create(filmId: Int): FilmViewModel
    }
}
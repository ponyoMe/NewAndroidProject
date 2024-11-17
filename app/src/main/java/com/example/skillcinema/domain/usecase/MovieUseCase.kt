package com.example.skillcinema.domain.usecase

import com.example.skillcinema.data.model.FilmImagesResponse
import com.example.skillcinema.data.repository.MovieRepository
import com.example.skillcinema.domain.model.Movie
import com.example.skillcinema.domain.model.MovieList
import retrofit2.Response
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun getPopularMovies(page: Int = 1): Result<MovieList> {
        return movieRepository.getPopularMovies(page)
    }

    suspend fun getTopRatedMovies(page: Int = 1): Result<MovieList> {
        return movieRepository.getTopRatedMovies(page)
    }

    suspend fun getNowShowingMovies(page: Int = 1): Result<MovieList> {
        return movieRepository.getNowShowingMovies(page)
    }

    suspend fun getFilmById(id: Int): Result<Movie> {
        return movieRepository.getFilmById(id)
    }

    suspend fun getImagesByFilmId(id: Int): Result<FilmImagesResponse> {
        return movieRepository.getImagesByFilmId(id)
    }
}
package com.example.skillcinema.domain.usecase

import com.example.skillcinema.data.repository.MovieRepository
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun getPopularMovies(page: Int = 1) =
        movieRepository.getPopularMovies(page)

    suspend fun getTopRatedMovies(page: Int = 1) =
        movieRepository.getTopRatedMovies(page)

    suspend fun getNowShowingMovies(page: Int = 1) =
        movieRepository.getNowShowingMovies(page)

    suspend fun getFilmById(id: Int) =
        movieRepository.getFilmById(id)

    suspend fun getImagesByFilmId(id: Int) =
        movieRepository.getImagesByFilmId(id = id)
}

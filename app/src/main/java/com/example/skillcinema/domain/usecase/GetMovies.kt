package com.example.skillcinema.domain.usecase

import com.example.skillcinema.data.repository.MovieRepository
import com.example.skillcinema.domain.model.MovieList

class GetMovies(private val movieRepository: MovieRepository) {

    suspend fun getPopularMovies(page: Int = 1): Result<MovieList> {
        return movieRepository.getPopularMovies(page)
    }

    suspend fun getTopRatedMovies(page: Int = 1): Result<MovieList> {
        return movieRepository.getTopRatedMovies(page)
    }

    suspend fun getNowShowingMovies(page: Int = 1): Result<MovieList> {
        return movieRepository.getNowShowingMovies(page)
    }
}

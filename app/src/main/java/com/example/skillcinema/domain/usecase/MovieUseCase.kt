package com.example.skillcinema.domain.usecase

import com.example.skillcinema.data.model.FilmImagesResponse
import com.example.skillcinema.data.model.SimilarMovies
import com.example.skillcinema.data.model.Staff
import com.example.skillcinema.data.model.StaffResponse
import com.example.skillcinema.data.repository.MovieRepository
import com.example.skillcinema.domain.model.Movie
import com.example.skillcinema.domain.model.MovieList
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun getPopularMovies(): Result<MovieList> {
        return movieRepository.getPopularMovies()
    }

    suspend fun getTopRatedMovies(): Result<MovieList> {
        return movieRepository.getTopRatedMovies()
    }

    suspend fun getNowShowingMovies(): Result<MovieList> {
        return movieRepository.getNowShowingMovies()
    }

    suspend fun getFilmById(id: Int): Result<Movie> {
        return movieRepository.getFilmById(id)
    }

    suspend fun getImagesByFilmId(id: Int): Result<FilmImagesResponse> {
        return movieRepository.getImagesByFilmId(id)
    }

    suspend fun getStaffByFilmId(id: Int): Result<List<StaffResponse>>{
        return movieRepository.getStaffByFilmId(id)
    }
    suspend fun getStaffDetails(id: Int):Result<Staff>{
        return movieRepository.getStaffDetails(id)
    }

    suspend fun getSimilarMovies(id: Int): SimilarMovies{
        return movieRepository.getSimilarMovies(id)
    }
}
package com.example.skillcinema.data.repository

import com.example.skillcinema.data.client.KinopoiskApi
import com.example.skillcinema.data.model.FilmImagesResponse
import com.example.skillcinema.data.model.MovieResponse
import com.example.skillcinema.data.model.StaffResponse
import com.example.skillcinema.domain.model.Movie
import com.example.skillcinema.domain.model.MovieList
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val kinopoiskApi: KinopoiskApi
) {

    suspend fun getPopularMovies(): Result<MovieList> {
        return safeApiCall {
            kinopoiskApi.getMovies(order = "RATING", ratingFrom = 7 , ratingTo = 10, page = 1)
        }
    }

    suspend fun getTopRatedMovies(): Result<MovieList> {
        return safeApiCall {
            kinopoiskApi.getMovies(order = "NUM_VOTE", ratingFrom = 8, page = 1)
        }
    }
    suspend fun getNowShowingMovies(): Result<MovieList> {
        return safeApiCall {
            kinopoiskApi.getMovies(order = "YEAR", yearFrom = 2024, yearTo = 2024, page = 1)
        }
    }

    suspend fun getFilmById(id: Int): Result<Movie> {
        return safeApiCall2 {
            kinopoiskApi.getFilmById(filmId = id)
        }

    }

    suspend fun getImagesByFilmId(id: Int): Result<FilmImagesResponse> {
        return safeApiCall2 {
            kinopoiskApi.getImagesByFilmId(filmId = id)
        }
    }

    suspend fun getStaffByFilmId(id: Int): Result<List<StaffResponse>>{
        return safeApiCall2 {
            kinopoiskApi.getStaffByFilmId(filmId = id)
        }
    }

    private suspend fun <T >safeApiCall2(apiCall: suspend () -> Response<T>): Result<T>{
        return try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                Result.success(response.body() ?: throw Exception("No data"))
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    private suspend fun safeApiCall(apiCall: suspend () -> Response<MovieResponse>): Result<MovieList> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    println("API response: ${it.items}")
                    Result.success(MovieList(it.items))
                } ?: Result.failure(Exception("No movies found"))
            } else {
                println("API error: ${response.code()} ${response.message()}")
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            println("Exception: ${e.message}")
            Result.failure(e)
        }
    }
}
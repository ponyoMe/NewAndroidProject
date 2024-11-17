package com.example.skillcinema.data.repository

import android.util.Log
import com.example.skillcinema.data.client.KinopoiskApi
import com.example.skillcinema.data.model.FilmImagesResponse
import com.example.skillcinema.data.model.MovieResponse
import com.example.skillcinema.domain.model.Movie
import com.example.skillcinema.domain.model.MovieList
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val kinopoiskApi: KinopoiskApi
) {

    suspend fun getPopularMovies(page: Int = 1): Result<MovieList> {
        return safeApiCall {
            kinopoiskApi.getPopularMovies(page)
        }
    }

    suspend fun getTopRatedMovies(page: Int = 1): Result<MovieList> {
        return safeApiCall {
            kinopoiskApi.getTopRatedMovies(page)
        }
    }
    suspend fun getNowShowingMovies(page: Int = 1): Result<MovieList> {
        return safeApiCall {
            kinopoiskApi.getNowShowingMovies(page)
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
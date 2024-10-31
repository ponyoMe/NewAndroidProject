package com.example.skillcinema.data.repository

import com.example.skillcinema.data.api.KinopoiskApi
import com.example.skillcinema.data.model.MovieResponse
import com.example.skillcinema.domain.model.MovieList
import retrofit2.Response

class MovieRepository(private val kinopoiskApi: KinopoiskApi) {

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

    private suspend fun safeApiCall(apiCall: suspend () -> Response<MovieResponse>): Result<MovieList> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    //println("API response: ${it.items}")
                    Result.success(MovieList(it.items))
                } ?: Result.failure(Exception("No movies found"))
            } else {
                //println("API error: ${response.code()} ${response.message()}")
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            println("Exception: ${e.message}")
            Result.failure(e)
        }
    }
}

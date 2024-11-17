package com.example.skillcinema.data.repository

import com.example.skillcinema.core.BaseRepository
import com.example.skillcinema.data.client.KinopoiskApi
import com.example.skillcinema.data.mapper.MovieMapper
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val kinopoiskApi: KinopoiskApi
) : BaseRepository {

    suspend fun getPopularMovies(page: Int = 1) =
        mappedSafeApiCall(MovieMapper) {
            kinopoiskApi.getMovies(
                order = "RATING",
                ratingFrom = 7,
                ratingTo = 10,
                page = page
            )
        }

    suspend fun getTopRatedMovies(page: Int = 1) =
        mappedSafeApiCall(MovieMapper) {
            kinopoiskApi.getMovies(order = "NUM_VOTE", ratingFrom = 8, page = page)
        }

    suspend fun getNowShowingMovies(page: Int = 1) =
        mappedSafeApiCall(MovieMapper) {
            kinopoiskApi.getMovies(order = "YEAR", yearFrom = 2024, yearTo = 2024, page = page)
        }

    suspend fun getFilmById(id: Int) =
        safeApiCall { kinopoiskApi.getFilmById(filmId = id) }

    suspend fun getImagesByFilmId(id: Int) =
        safeApiCall { kinopoiskApi.getImagesByFilmId(filmId = id) }
}

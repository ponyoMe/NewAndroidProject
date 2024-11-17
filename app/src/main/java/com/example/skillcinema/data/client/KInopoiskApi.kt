package com.example.skillcinema.data.client

import com.example.skillcinema.data.model.FilmImagesResponse
import com.example.skillcinema.data.model.MovieResponse
import com.example.skillcinema.data.model.StaffResponse
import com.example.skillcinema.domain.model.Movie
import com.example.testing.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import com.example.skillcinema.utils.Constants.BASE_URL
import retrofit2.http.Path

private const val API_KEY = "1f4ab51f-059f-47bf-a4f2-319ddbdaaef4"

interface KinopoiskApi {

    @GET("v2.2/films")
    suspend fun getMovies(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Query("order") order: String,
        @Query("type") type: String = "ALL",
        @Query("ratingFrom") ratingFrom: Int? = null,
        @Query("ratingTo") ratingTo: Int? = null,
        @Query("yearFrom") yearFrom: Int? = null,
        @Query("yearTo") yearTo: Int? = null,
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("v2.2/films/{id}")
    suspend fun getFilmById(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Path("id") filmId: Int
    ): Movie

    @GET("v1/staff/{id}")
    suspend fun getStaffByFilmId(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Path("id") filmId: Int
    ): List<StaffResponse>

    @GET("v2.2/films/{id}/images")
    suspend fun getImagesByFilmId(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Path("id") filmId: Int,
        @Query("page") page: Int = 1
    ): FilmImagesResponse
}
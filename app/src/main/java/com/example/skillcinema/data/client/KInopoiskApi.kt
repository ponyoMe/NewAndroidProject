package com.example.skillcinema.data.api

import com.example.skillcinema.data.model.MovieResponse
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

private const val API_KEY= BuildConfig.API_KEY

interface KinopoiskApi {
    @GET("films")
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

    suspend fun getPopularMovies(page: Int = 1): Response<MovieResponse> {
        return getMovies(order = "RATING", ratingFrom = 7, ratingTo = 10, page = page)
    }

    suspend fun getTopRatedMovies(page: Int = 1): Response<MovieResponse> {
        return getMovies(order = "NUM_VOTE", ratingFrom = 8, page = page)
    }

    suspend fun getNowShowingMovies(page: Int = 1): Response<MovieResponse> {
        return getMovies(order = "YEAR", yearFrom = 2024, yearTo = 2024, page = page)
    }
}

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()

private val retrofit= Retrofit.Builder()
    .baseUrl(BASE_URL).client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(KinopoiskApi::class.java)
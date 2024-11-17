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

    @GET("v2.2/films/{id}")
    suspend fun getFilmById(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Path("id") filmId: Int
    ):Response<Movie>

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
    ): Response<FilmImagesResponse>

    @GET("v2.2/films/{id}/images")
    suspend fun getSimilarsFilms(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Path("id") filmId: Int,
        @Query("page") page: Int = 1
    ): FilmImagesResponse
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
package com.example.skillcinema.data.client

import com.example.skillcinema.data.model.FilmImagesResponse
import com.example.skillcinema.data.model.MovieResponse
import com.example.skillcinema.data.model.StaffResponse
import com.example.skillcinema.domain.model.Movie
<<<<<<< HEAD
import com.example.testing.BuildConfig
=======
>>>>>>> refactor/project-structure
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

<<<<<<< HEAD
private const val API_KEY = "1f4ab51f-059f-47bf-a4f2-319ddbdaaef4"
=======
private const val API_KEY = "2ff89ed2-6a23-4f56-8964-989713704fbe"
>>>>>>> refactor/project-structure

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
<<<<<<< HEAD
    ): Movie

    @GET("v1/staff/{id}")
    suspend fun getStaffByFilmId(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Path("id") filmId: Int
    ): List<StaffResponse>
=======
    ):Response<Movie>

    @GET("v1/staff")
    suspend fun getStaffByFilmId(
        @Query("filmId") filmId: Int,
        @Header("X-API-KEY") apiKey: String = API_KEY,
    ): Response<List<StaffResponse>>
>>>>>>> refactor/project-structure

    @GET("v2.2/films/{id}/images")
    suspend fun getImagesByFilmId(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Path("id") filmId: Int,
<<<<<<< HEAD
        @Query("page") page: Int = 1
    ): FilmImagesResponse
}
=======
    ): Response<FilmImagesResponse>

//    @GET("v2.2/films/{id}/images")
//    suspend fun getSimilarFilms(
//        @Header("X-API-KEY") apiKey: String = API_KEY,
//        @Path("id") filmId: Int,
//        @Query("page") page: Int = 1
//    ): FilmImagesResponse
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

val api: KinopoiskApi = retrofit.create(KinopoiskApi::class.java)
>>>>>>> refactor/project-structure

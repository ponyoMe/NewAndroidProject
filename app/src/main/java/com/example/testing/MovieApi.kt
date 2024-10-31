package com.example.testing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


private const val BASE_URL="https://kinopoiskapiunofficial.tech/api/v2.2/"
private const val API_KEY= "1b06bc9e-3390-4a7b-b68d-0ef9974be9e0"

interface KinopoiskApi {
    @GET("films")
    suspend fun getPopularMovies(
        @Header("X-API-KEY") apiKey: String,
        @Query("order") order:String="RATING",
        @Query("type") type:String= "ALL",
        @Query("ratingFrom") ratingFrom:Int=7,
        @Query("ratingTo") ratingTo:Int=10,
        @Query("page") page : Int = 1
    ): Response<MovieResponse>

    @GET("films")
    suspend fun getTopRatedMovies(
        @Header("X-API-KEY") apiKey: String,
        @Query("order") order:String="NUM_VOTE",
        @Query("type") type:String="ALL",
        @Query("ratingFrom") ratingFrom: Int=8,
        @Query("page") page : Int = 1
    ): Response<MovieResponse>

    @GET("films")
    suspend fun getNowShowingMovies(
        @Header("X-API-KEY") apiKey: String,
        @Query("order") order:String="YEAR",
        @Query("type") type:String="ALL",
        @Query("yearFrom") yearFrom:Int=2024,
        @Query("yearTo") yearTo:Int=2024,
        @Query("page") page : Int = 1
    ): Response<MovieResponse>
}

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()

private val retrofit=Retrofit.Builder()
    .baseUrl(BASE_URL).client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(KinopoiskApi::class.java)


class MovieRepository {
    private val api = retrofit.create(KinopoiskApi::class.java)

    suspend fun getPopularMovies(): List<Movie> {
        return try {
            val response = api.getPopularMovies(apiKey = API_KEY)
            if (response.isSuccessful) {
                response.body()?.items ?: emptyList()
            } else {
                throw Exception("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Failed to fetch popular movies: ${e.message}")
        }
    }

    suspend fun getTopRatedMovies(): List<Movie> {
        return try {
            val response = api.getTopRatedMovies(apiKey = API_KEY)
            if (response.isSuccessful) {
                response.body()?.items ?: emptyList()
            } else {
                throw Exception("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Failed to fetch top rated movies: ${e.message}")
        }
    }

    suspend fun getNowShowingMovies(): List<Movie> {
        return try {
            val response = api.getNowShowingMovies(
                apiKey = API_KEY
            )
            if (response.isSuccessful) {
                response.body()?.items ?: emptyList()
            } else {
                throw Exception("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Failed to fetch now showing movies: ${e.message}")
        }
    }
}

sealed class MovieScreenState {
    object Initial : MovieScreenState()
    object Loading : MovieScreenState()
    data class Success(
        val popularMovies: List<Movie> = emptyList(),
        val topRatedMovies: List<Movie> = emptyList(),
        val nowShowingMovies: List<Movie> = emptyList(),
        val errors: Map<String, String> = emptyMap()
    ) : MovieScreenState()
    data class Error(val message: String) : MovieScreenState()

}

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository()

    private val _state = MutableStateFlow<MovieScreenState>(MovieScreenState.Initial)
    val state: StateFlow<MovieScreenState> = _state.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _state.value = MovieScreenState.Loading

            val errors = mutableMapOf<String,String>()
            var popularMovies = emptyList<Movie>()
            var topRatedMovies= emptyList<Movie>()
            var nowShowingMovies= emptyList<Movie>()

            try{
                popularMovies=repository.getPopularMovies()
            }catch (e:Exception){
                errors["popular"]=e.message ?: "Failed to fetch popular movies"
            }

            try{
                topRatedMovies=repository.getTopRatedMovies()
            }catch(e:Exception){
                errors["topRated"]=e.message?:"Failed to fetch top rated movies"
            }

            try{
                nowShowingMovies=repository.getNowShowingMovies()
            }catch(e:Exception){
                errors["nowShowing"]=e.message?:"Failed to fetch now showing movies"
            }

            _state.value = MovieScreenState.Success(
                popularMovies = popularMovies,
                topRatedMovies = topRatedMovies,
                nowShowingMovies = nowShowingMovies,
                errors = errors
            )
        }
    }

    fun retry() {
        loadMovies()
    }
}

package com.example.skillcinema.domain.usecase

<<<<<<< HEAD
import com.example.skillcinema.data.repository.MovieRepository
=======
import com.example.skillcinema.data.model.FilmImagesResponse
import com.example.skillcinema.data.model.StaffResponse
import com.example.skillcinema.data.repository.MovieRepository
import com.example.skillcinema.domain.model.Movie
import com.example.skillcinema.domain.model.MovieList
>>>>>>> refactor/project-structure
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

<<<<<<< HEAD
    suspend fun getPopularMovies(page: Int = 1) =
        movieRepository.getPopularMovies(page)

    suspend fun getTopRatedMovies(page: Int = 1) =
        movieRepository.getTopRatedMovies(page)

    suspend fun getNowShowingMovies(page: Int = 1) =
        movieRepository.getNowShowingMovies(page)

    suspend fun getFilmById(id: Int) =
        movieRepository.getFilmById(id)

    suspend fun getImagesByFilmId(id: Int) =
        movieRepository.getImagesByFilmId(id = id)
}
=======
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
}
>>>>>>> refactor/project-structure

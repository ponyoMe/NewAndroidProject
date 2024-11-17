package com.example.skillcinema.data.mapper

import com.example.skillcinema.core.BaseMapper
import com.example.skillcinema.data.model.Country
import com.example.skillcinema.data.model.Genre
import com.example.skillcinema.data.model.MovieResponse
import com.example.skillcinema.domain.model.Movie
import retrofit2.Response

object MovieMapper : BaseMapper<Response<MovieResponse>, List<Movie>?> {
    override fun map(source: Response<MovieResponse>): List<Movie>? {
        val movieResponse = source.body() ?: return null

        return movieResponse.items.map { movie ->
            Movie(
                kinopoiskId = movie.kinopoiskId,
                imdbId = movie.imdbId,
                nameRu = movie.nameRu,
                nameEn = movie.nameEn,
                nameOriginal = movie.nameOriginal,
                countries = movie.countries.map { Country(it.country) },
                genres = movie.genres.map { Genre(it.genre) },
                ratingKinopoisk = movie.ratingKinopoisk,
                ratingImdb = movie.ratingImdb,
                year = movie.year,
                filmLength = movie.filmLength,
                ratingAgeLimits = movie.ratingAgeLimits,
                type = movie.type,
                posterUrl = movie.posterUrl,
                posterUrlPreview = movie.posterUrlPreview
            )
        }
    }
}
package com.example.skillcinema.presentation.film

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.CircularProgressIndicator
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.navigation.NavController
import com.example.skillcinema.presentation.film.components.FilmDetailItem
import com.example.skillcinema.presentation.film.components.FilmImages
import com.example.skillcinema.presentation.film.components.HeaderText
import com.example.skillcinema.presentation.film.components.MovieDescriptionItem
import com.example.skillcinema.presentation.film.components.SimilarFilmsList
import com.example.skillcinema.presentation.film.components.StaffList
import com.example.skillcinema.presentation.film.states.FilmState
import com.example.skillcinema.presentation.film.states.ImagesState
import com.example.skillcinema.presentation.film.states.SimilarFilmState
import com.example.skillcinema.presentation.film.states.StaffState
import com.example.testing.R

@Composable
fun FilmScreen(
    filmId: Int,
    navController: NavController
) {
    val viewModel =
        hiltViewModel<FilmViewModel, FilmViewModel.ViewModelFactory> { factory ->
            factory.create(filmId)
        }

    val filmState by viewModel.filmState.collectAsState()
    val imagesState by viewModel.imagesState.collectAsState()
    val staffState by viewModel.staffState.collectAsState()
    val similarMoviesState by viewModel.similarMoviesState.collectAsState()
    val watched by viewModel.hasWatched.collectAsState()
    val fav by viewModel.fav.collectAsState()
    val saved by viewModel.saved.collectAsState()

    LaunchedEffect(filmId) {
        viewModel.fetchFilmById(filmId)
    }

    when(filmState){
        is FilmState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        is FilmState.Success -> {
            val film = (filmState as FilmState.Success).movie
            val filmImages = (imagesState as? ImagesState.Success)?.images ?: emptyList()
            val filmStaff = (staffState as? StaffState.Success)?.staff ?: emptyList()
            val similarFilms = (similarMoviesState as? SimilarFilmState.Success)?.similarMovies ?: emptyList()

            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
            ){
                item {
                    FilmDetailItem(film, navController, viewModel, watched, fav, saved)
                }

                val actors = filmStaff.filter { it.professionKey == "ACTOR"}
                val directors = filmStaff.filter { it.professionKey != "ACTOR"}

                item{
                    MovieDescriptionItem(movie = film)
                }

                if (actors.isNotEmpty()){
                    item {
                        HeaderText(content = "В фильме снимались", listSize = actors.size) { }
                    }

                    item{
                        StaffList(actors, (actors.size + 3)/4, navController = navController, )
                    }
                }

                if(directors.isNotEmpty()){
                    item {
                        HeaderText(content = "Над фильмом работали", listSize = directors.size) { }
                    }

                    item {
                        StaffList(directors, (directors.size + 1)/2, navController = navController)
                    }
                }

                item {
                    HeaderText(content = "Галерея", listSize = filmImages.size) {
                        navController.navigate("gallery/${filmId}")
                    }
                }

                item{
                    FilmImages(filmImages){ image->
                        Log.d("FilmScreen", "Clicked on image: ${image.imageUrl}")
                    }
                }

                item {
                    HeaderText(content = "Похожие фильмы", listSize = similarFilms.size) {}
                }

                item {
                    SimilarFilmsList(similarFilms, navController)
                }
            }

        }
        is FilmState.Error -> TODO()
    }
}


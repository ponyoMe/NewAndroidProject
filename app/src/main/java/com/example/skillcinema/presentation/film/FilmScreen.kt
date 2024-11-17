package com.example.skillcinema.presentation.film

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.IconButton
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.remember
import com.example.skillcinema.data.model.FilmImage

@Composable
fun FilmScreen(filmId: Int) {
    val viewModel =
        hiltViewModel<FilmViewModel, FilmViewModel.ViewModelFactory> { factory ->
            factory.create(filmId)
        }

    val filmState by viewModel.filmState.collectAsState()

    LaunchedEffect(filmId) {
        viewModel.fetchFilmById(filmId)
    }

    when(filmState){
        is FilmState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                //contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        is FilmState.Success -> {
            val film = (filmState as FilmState.Success).movie

            Column (
                modifier = Modifier
                    .fillMaxSize()
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .background(
                            Color.Black
                        )
                ){
                    Text(
                        text = film.nameOriginal.toString(),
                        modifier = Modifier
                            .align(Alignment.Center),
                        color = Color.White
                    )

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .padding(top = 24.dp),
                        horizontalArrangement = Arrangement.Center,
                    ){
                        Text(
                            text = film.ratingKinopoisk.toString(),
                            color = Color.White,

                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = film.nameRu.toString(),
                            color = Color.White,
                            )
                    }

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .padding(top = 48.dp),
                        horizontalArrangement = Arrangement.Center,
                    ){
                        Text(
                            text = film.year.toString(),
                            color = Color.White,
                            )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = film.genres.joinToString(separator = ", ") { it.genre },
                            color = Color.White,
                        )
                    }

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .padding(top = 72.dp),
                        horizontalArrangement = Arrangement.Center,
                    ){
                        Text(
                            text = film.countries.joinToString(separator = ", ") { it.country },
                            color = Color.White,
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "${film.filmLength?.div(60)} ч ${film.filmLength?.rem(60)} мин",
                            color = Color.White,
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "${film.ratingAgeLimits?.replace("age","")}+",
                            color = Color.White,
                        )
                    }
                }
            }

        }
        is FilmState.Error -> TODO()
    }
}

@Composable
fun IconItem(icon: ImageVector, contentDescription: String, modifier: Modifier = Modifier){
    IconButton(
        onClick = {},
        modifier = Modifier
    ){
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun FilmImages(filmImages: List<FilmImage>){
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filmImages){ image ->
            AsyncImage(
                model = image.
            )
        }
    }
}



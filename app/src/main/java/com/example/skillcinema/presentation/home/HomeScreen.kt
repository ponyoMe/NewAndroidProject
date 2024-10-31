package com.example.skillcinema.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.skillcinema.data.model.Movie

@Composable
fun MovieItemView(item: Movie, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(120.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.Start
    ) {
        Box {
            AsyncImage(
                model = item.posterUrlPreview,
                contentDescription = item.nameRu,
                modifier = Modifier
                    .width(110.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            if (item.ratingKinopoisk != null) {
                Text(
                    text = item.ratingKinopoisk.toString(),
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .background(Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(4.dp))
                        .padding(4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        (item.nameRu ?: item.nameEn ?: item.nameOriginal)?.let {
            Text(text = it, fontSize = 16.sp, maxLines = 2)
        }
        Text(
            text = item.genres.firstOrNull()?.genre ?: "",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun MovieSection(
    title: String,
    movies: List<Movie>,
    onClick: (Movie) -> Unit,
    onShowAllClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Все", color = Color.Blue, modifier = Modifier.clickable { onShowAllClick() })
        }

        LazyRow(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            items(movies) { movie ->
                MovieItemView(item = movie, onClick = { onClick(movie) })
            }

            item {
                ShowAllButton(onClick = onShowAllClick)
            }
        }
    }
}

@Composable
fun ShowAllButton(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Show all",
                tint = Color.Blue,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Показать все",
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Composable
fun MovieGrid(movies: List<Movie>, genre: String, onClick: (Movie) -> Unit, onBack: () -> Unit) {

    Column {
        Spacer(modifier = Modifier.height(30.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            BackButton(onClick = onBack)
            Spacer(modifier = Modifier.width(70.dp))

            Text(
                text = genre,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }


        Spacer(modifier = Modifier.height(60.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().padding(start = 25.dp, end = 25.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(movies) { movie ->
                MovieGridItem(movie) {
                    Log.d("MovieGrid", "Кликнули по фильму: ${movie.nameRu}")
                    onClick(movie)
                }
            }
        }
    }
}

@Composable
fun MovieGridItem(item: Movie, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(start = 20.dp)
            .width(120.dp)
            .clickable { onClick() },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            AsyncImage(
                model = item.posterUrlPreview,
                contentDescription = item.nameRu,
                modifier = Modifier
                    .width(110.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            if (item.ratingKinopoisk != null) {
                Text(
                    text = item.ratingKinopoisk.toString(),
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .background(Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(4.dp))
                        .padding(4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        (item.nameRu ?: item.nameEn ?: item.nameOriginal)?.let {
            Text(text = it, fontSize = 16.sp, maxLines = 2)
        }
        Text(text = item.genres.firstOrNull()?.genre ?: "", fontSize = 12.sp, color = Color.Gray)
        Text(text = "${item.year}", fontSize = 12.sp, color = Color.Gray)
    }
}


@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(onClick = { onClick() }) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back"
        )
    }
}

@Composable
fun HomeSc(
    viewModel: MovieViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    var showGrid by remember { mutableStateOf(false) }
    var selectedGenre by remember { mutableStateOf("") }
    var selectedMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }

    when (state) {
        is MovieScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is MovieScreenState.Success -> {
            val movies = state as MovieScreenState.Success

            if (showGrid) {
                MovieGrid(
                    movies = selectedMovies,
                    genre = selectedGenre,
                    onClick = { movie ->
                        Log.d("HomePage", "Clicked on movie: ${movie.nameRu}")
                    },
                    onBack = { showGrid = false }
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(50.dp))
                        Text(
                            text = "Skillcinema",
                            color = Color(0xFF272727),
                            fontSize = 26.sp,
                            modifier = Modifier.padding(start = 14.dp)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    // Now Showing Section
                    item {
                        if (movies.errors.containsKey("nowShowing")) {
                            ErrorSection(
                                error = movies.errors["nowShowing"] ?: "",
                                onRetry = { viewModel.retry() }
                            )
                        } else {
                            MovieSection(
                                title = "Премьеры",
                                movies = movies.nowShowingMovies,
                                onClick = { movie ->
                                    Log.d("HomePage", "Clicked on movie: ${movie.nameRu}")
                                },
                                onShowAllClick = {
                                    selectedGenre = "Премьеры"
                                    selectedMovies = movies.nowShowingMovies
                                    showGrid = true
                                }
                            )
                        }
                    }

                    // Popular Movies Section
                    item {
                        if (movies.errors.containsKey("popular")) {
                            ErrorSection(
                                error = movies.errors["popular"] ?: "",
                                onRetry = { viewModel.retry() }
                            )
                        } else {
                            MovieSection(
                                title = "Популярное",
                                movies = movies.popularMovies,
                                onClick = { movie ->
                                    Log.d("HomePage", "Clicked on movie: ${movie.nameRu}")
                                },
                                onShowAllClick = {
                                    selectedGenre = "Популярное"
                                    selectedMovies = movies.popularMovies
                                    showGrid = true
                                }
                            )
                        }
                    }

                    // Top Rated Movies Section
                    item {
                        if (movies.errors.containsKey("topRated")) {
                            ErrorSection(
                                error = movies.errors["topRated"] ?: "",
                                onRetry = { viewModel.retry() }
                            )
                        } else {
                            MovieSection(
                                title = "Топ рейтинга",
                                movies = movies.topRatedMovies,
                                onClick = { movie ->
                                    Log.d("HomePage", "Clicked on movie: ${movie.nameRu}")
                                },
                                onShowAllClick = {
                                    selectedGenre = "Топ рейтинга"
                                    selectedMovies = movies.topRatedMovies
                                    showGrid = true
                                }
                            )
                        }
                    }
                }
            }
        }

        else -> {}
    }
}


@Composable
fun ErrorSection(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = error,
            color = Color.Red,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        HomeSc()
    }
}

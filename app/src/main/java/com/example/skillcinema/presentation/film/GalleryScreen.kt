package com.example.skillcinema.presentation.film

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.skillcinema.presentation.film.state.ImagesState

@Composable
fun GalleryScreen(
    filmId: Int,
    viewModel: FilmViewModel = hiltViewModel<FilmViewModel, FilmViewModel.ViewModelFactory> { factory ->
        factory.create(filmId)
    },
    navController: NavController
) {
    val images by viewModel.imagesState.collectAsState()

    LaunchedEffect(filmId) {
        viewModel.fetchImagesByFilmId(filmId)
    }

    when (images) {
        is ImagesState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.CircularProgressIndicator()
            }
        }
        is ImagesState.Success -> {
            val imagesToDisplay = (images as ImagesState.Success).images

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier = Modifier
                            .size(34.dp)
                            .padding(end = 16.dp)
                            .clickable { navController.popBackStack() }
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Галерея", fontSize = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    itemsIndexed(imagesToDisplay) { index, image ->
                        if (index % 3 == 2) {
                            AsyncImage(
                                model = image.imageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(14 / 9f)
                            )
                        } else if (index % 3 == 0) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                val secondImage = imagesToDisplay.getOrNull(index + 1)
                                AsyncImage(
                                    model = image.imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .weight(2f)
                                        .aspectRatio(14 / 9f)
                                )
                                if (secondImage != null) {
                                    AsyncImage(
                                        model = secondImage.imageUrl,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .weight(2f)
                                            .aspectRatio(14 / 9f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        is ImagesState.Error -> {
            val error = (images as ImagesState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = error, fontSize = 16.sp, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
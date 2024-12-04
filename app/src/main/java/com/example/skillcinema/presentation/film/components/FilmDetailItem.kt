package com.example.skillcinema.presentation.film.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.skillcinema.data.model.Film
import com.example.skillcinema.domain.model.Movie
import com.example.skillcinema.presentation.film.FilmViewModel
import com.example.testing.R

@Composable
fun FilmDetailItem(
    film: Movie,
    navController: NavController,
    viewModel: FilmViewModel,
    watched: Boolean,
    fav : Boolean,
    saved : Boolean
){

    Column(){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(
                    Color.Black
                )
        ){
            AsyncImage(
                model = film.coverUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .height(400.dp),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(R.drawable.img),
                        contentDescription = "Back",
                        modifier = Modifier.size(36.dp),
                        tint = Color.Black
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = (film.nameOriginal ?: film.nameEn ?: film.nameRu ?: "").toString(),
                    modifier = Modifier
                        .padding(top = 210.dp),
                    style = TextStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight(700),
                        color = Color(181,181,201),
                    ),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                        //.align(Alignment.Center)
                        //.padding(top = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                ){
                    Text(
                        text = film.ratingKinopoisk.toString(),
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(181,181,201),
                            )
                        )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = film.nameRu.toString(),
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(181,181,201),
                        )
                    )
                }

                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ){
                    Text(
                        text = film.year.toString(),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(181,181,201),
                        )
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    film.genres?.let {
                        it?.joinToString(separator = ", ") { it.genre.toString() }?.let { it1 ->
                            Text(
                                text = it1,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(181,181,201),
                                )
                            )
                        }
                    }
                }

                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                        //.align(Alignment.Center)
                        //.padding(top = 72.dp),
                    horizontalArrangement = Arrangement.Center,
                ){
                    film.countries?.let {
                        Text(
                            text = it.joinToString(separator = ", ") { it.country.toString() },
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight(400),
                                color = Color(181,181,201),
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "${film.filmLength?.div(60)} ч ${film.filmLength?.rem(60)} мин",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(181,181,201),
                        )
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "${film.ratingAgeLimits?.replace("age","")}+",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(181,181,201),
                        )
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
//                    IconItem(R.drawable.ic_like,
//                        onClick = {viewModel.toggleFavorite()},
//                        tint = if (isFavorite) Color.Red else Color.White)
                    Icon(painter = painterResource(R.drawable.ic_like), contentDescription = null,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable {
                               viewModel.favMovies(film)
                            },
                        tint = if(fav) Color.Red else Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(painter = painterResource(R.drawable.ic_zakladki) ,
                        contentDescription = null,
                        tint =  if(saved) Color.Blue else Color.White,
                        modifier = Modifier.clickable {
                            viewModel.saved(film)
                        })
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(painter = painterResource(R.drawable.ic_dont_show), contentDescription = null,
                        tint = if (watched) Color(0xFF3D3BFF) else Color.White,
                        modifier = Modifier.clickable {
                            if(watched){
                                viewModel.deleteWatched(film.kinopoiskId)
                            }else{
                                viewModel.insertToDb(film)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(painter = painterResource(R.drawable.ic_share) ,
                        contentDescription = null,
                        tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(painter = painterResource(R.drawable.ic_other) ,
                        contentDescription = null,
                        tint = Color.White)

                }
            }

        }
    }
}



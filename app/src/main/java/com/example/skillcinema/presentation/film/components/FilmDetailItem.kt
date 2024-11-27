package com.example.skillcinema.presentation.film.components

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
import com.example.testing.R

@Composable
fun FilmDetailItem(
    film: Movie,
    navController: NavController
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

                    Text(
                        text = film.genres.joinToString(separator = ", ") { it.genre },
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
                        //.align(Alignment.Center)
                        //.padding(top = 72.dp),
                    horizontalArrangement = Arrangement.Center,
                ){
                    Text(
                        text = film.countries.joinToString(separator = ", ") { it.country },
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(181,181,201),
                        )
                    )

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
                    IconItem(R.drawable.ic_like, onClick = {})
                    IconItem(R.drawable.ic_zakladki, onClick = {})
                    IconItem(R.drawable.ic_dont_show, onClick = {})
                    IconItem(R.drawable.ic_share, onClick = {})
                    IconItem(R.drawable.ic_other, onClick = {})
                }
            }

        }
    }
}

@Composable
fun IconItem(iconResId: Int, onClick: () -> Unit){
    Box(
        modifier = Modifier
            .size(22.dp, 17.dp)
            .clickable (onClick = onClick),
        contentAlignment = Alignment.Center
    ){
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(width = 22.dp, height = 17.dp),
            tint = Color.Black
        )
    }
}

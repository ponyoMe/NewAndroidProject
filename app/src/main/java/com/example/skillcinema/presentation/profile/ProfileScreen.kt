package com.example.skillcinema.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.testing.R

@Composable
fun ProfileScreen () {

    val movieViewModel: ProfileViewModel = hiltViewModel()

    val watchedMovie by movieViewModel.watchedMovie.collectAsState()
    val fav by movieViewModel.favCount.collectAsState()
    val saved by movieViewModel.savedCount.collectAsState()

    LaunchedEffect(Unit) {
        movieViewModel.getMovies()
    }

    Column( modifier = Modifier.fillMaxSize()
        ){

            if(!watchedMovie.Loading && watchedMovie.Error.isBlank()){
                Spacer(modifier = Modifier.height(40.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    ,horizontalArrangement = Arrangement.SpaceBetween){
                    Text(text = "Посмотрено", fontSize = 20.sp,
                        fontWeight = FontWeight(500)
                    )

                    Text(text= watchedMovie.list.size.toString(),
                        color = Color.Blue)
                }
                Spacer(modifier = Modifier.height(20.dp))
                LazyRow(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)) {

                    items(watchedMovie.list){movie->
                        Column(modifier = Modifier.padding(end = 10.dp)){
                           Box(
                               modifier = Modifier
                                   .clip(RoundedCornerShape(8.dp))
                                   .fillMaxWidth()
                                   .size(width = 110.dp, height = 150.dp)
                            ){
                             AsyncImage(
                                model = movie.posterurl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                             )

                            if (movie.movieId != null) {
                                Text(
                                    text = movie.rating.toString(),
                                    fontSize = 10.sp,
                                    color = Color.White,
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .background(
                                            Color.Blue,
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                        .padding(4.dp)
                                )
                            }
                        }
                        Spacer(Modifier.height(8.dp))

                            movie.name.let {
                                Text(
                                    text = it,
                                    maxLines = 2,
                                    fontSize = 14.sp,
                                    //modifier = Modifier.fillMaxWidth(),
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.width(110.dp)
                                )
                            }
                    }
                  }
                  item {
                      Column(modifier = Modifier
                          .width(111.dp)
                          .height(150.dp)
                          .padding(start = 10.dp),
                          verticalArrangement = Arrangement.Center,
                          ){

                          Box(
                              modifier = Modifier
                                  .size(50.dp)
                                  .clip(RoundedCornerShape(30.dp))
                                  .background(color = Color(0xFFFFFFFF))
                                  .clickable { movieViewModel.deleteAll()}
                                  .padding(start = 10.dp),
                              contentAlignment = Alignment.Center
                          ){
                              Icon(Icons.Default.Delete, contentDescription = null,
                                  modifier = Modifier
                                      )

                          }

                          Text(text = "Очистить историю",
                              fontSize = 14.sp,
                              fontWeight = FontWeight(400))
                      }
                  }
                }

                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "Коллекции", fontSize = 16.sp, fontWeight = FontWeight(500),
                    modifier = Modifier.padding(start = 20.dp))
                Spacer(modifier = Modifier.height(30.dp))

                Row(modifier = Modifier.fillMaxWidth()
                    .padding(10.dp)){
                    Collection(R.drawable.ic_like, "Favorite", fav.toString())
                    Spacer(modifier = Modifier.width(10.dp))
                    Collection(R.drawable.ic_zakladki, "Saved", saved.toString())
                }
            }
        }
}

@Composable
fun Collection(
    icon : Int,
    name : String,
    size : String
){
    Column(
        modifier = Modifier.size(180.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Icon(painter = painterResource(icon), contentDescription = null,
            modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = name)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = size)

    }
}

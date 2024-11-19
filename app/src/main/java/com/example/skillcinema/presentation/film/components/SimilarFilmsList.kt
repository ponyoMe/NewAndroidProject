package com.example.skillcinema.presentation.film.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.skillcinema.data.model.SimilarMovie
import com.example.skillcinema.data.model.SimilarMovies
import com.example.skillcinema.presentation.film.state.SimilarFilmState

@Composable
fun SimilarFilmsList(similar: List<SimilarMovie>, navController: NavController){
    Column {
        LazyRow (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(similar) { similarFilm ->
                SimilarFilmItem(similarFilm, onclick = {
                    navController.navigate("movie/${similarFilm.filmId}")
                })
            }
        }
    }
}

@Composable
fun SimilarFilmItem(similarFilm: SimilarMovie, onclick: () -> Unit){
    Column (
        modifier = Modifier
            .width(120.dp)
            .clickable {
                onclick()
            }
    ){
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .size(width = 111.dp, height = 200.dp)
        ){
            AsyncImage(
                model = similarFilm.posterUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Spacer(Modifier.height(8.dp))

        Text(
            text = similarFilm.nameOriginal,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                color = Color(39,39,39)
            )
        )
    }
}
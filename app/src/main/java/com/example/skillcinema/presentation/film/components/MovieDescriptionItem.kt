package com.example.skillcinema.presentation.film.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillcinema.domain.model.Movie

@Composable
fun MovieDescriptionItem(movie: Movie){
    Column (
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .padding(top = 25.dp)
    ){
        Text(
            text = movie.shortDescription,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(700),
                color = Color(39,39,39)
            )
        )

        Text(
            modifier = Modifier.padding(top = 20.dp),
            maxLines = 5,
            overflow = TextOverflow.Ellipsis,
            text = movie.description,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color(39,39,39)
            )
        )
    }
}
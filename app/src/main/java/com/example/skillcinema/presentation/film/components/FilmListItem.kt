package com.example.skillcinema.presentation.film.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.skillcinema.data.model.FilmImage

@Composable
fun FilmImages(filmImages: List<FilmImage>, onItemClick: (FilmImage) -> Unit){
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filmImages){ image ->
            AsyncImage(
                model = image.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(200.dp)
                    .clickable { onItemClick(image) }
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

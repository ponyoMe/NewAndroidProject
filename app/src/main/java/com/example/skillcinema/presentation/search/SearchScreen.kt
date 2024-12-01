package com.example.skillcinema.presentation.search

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.skillcinema.data.model.MovieSearch
import com.example.skillcinema.domain.model.Movie


@Composable
fun SearchScreen (
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchState by viewModel.searchState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.Center
    ){
        SearchField(
            searchQuery = searchQuery,
            onQueryChanged = viewModel::onQueryTextChanged,
            onSearchClick = {viewModel.searchMovies()},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .padding(horizontal = 26.dp)
        )
        when(searchState){
            is SearchState.Loading -> {
                CircularProgressIndicator()
            }

            is SearchState.Success -> {
                val movies = (searchState as SearchState.Success).movies
                Log.d("Search Screen", "movie size: ${movies.size}")
                MovieList(movies, navController)
            }

            is SearchState.Empty -> {
                Text(
                    text = "К сожалению по вашему запросу ничего не найдено"
                    ,modifier = Modifier.padding(top = 16.dp)
                )
            }

            is SearchState.Error -> {
                val errorMessage = (searchState as SearchState.Error).message
                Text(text = errorMessage, modifier = Modifier.padding(top = 16.dp))
            }
        }
    }

}


@Composable
fun MovieList(movies: List<MovieSearch>, navController: NavController){
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = 16.dp)
    ) {
        items(movies){ movie ->
            MovieItem(movie,navController)
        }
    }
}

@Composable
fun MovieItem(movie: MovieSearch, navController: NavController){
    Row(modifier = Modifier
        .fillMaxSize()
        .padding(start = 26.dp)
        .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center)
    {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = null,
            modifier = Modifier
                .width(96.dp)
                .height(132.dp)
                .clickable { navController.navigate("movie/${movie.filmId}") },
        )

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ){
            movie.nameRu?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(600),
                    color = Color(39, 39, 39),
                ) }
            Text(
                text = movie.year.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = Color(131, 131, 131),
            )
            Text(
                text = movie.genres.joinToString(separator = ", ") { it.genre },
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(131, 131, 131),
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SearchField(
    searchQuery: String,
    onQueryChanged: (String) -> Unit,
    onSearchClick: () -> Unit,
    //onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = searchQuery,
        onValueChange = onQueryChanged,
        modifier = modifier
            .clip(MaterialTheme.shapes.extraLarge)
            .background(Color(0xFFE0E0E0))
            .indicatorLine(
                enabled = false,
                isError = false,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                focusedIndicatorLineThickness = 0.dp,
                unfocusedIndicatorLineThickness = 0.dp
            ),
        placeholder = { Text(text = "Фильмы, актёры, режиссёры") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onSearchClick()
                        keyboardController?.hide()
                    }
            )
        },

        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 48.dp)
            ){
                Canvas(
                    modifier = Modifier
                        .height(16.dp)
                        .width(1.dp)
                        .background(Color.Gray)
                        .padding(end = 18.dp)
                ) {}
            }
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .padding(start = 4.dp)
                    //.clickable { onFilterClick() }
            )
        },

    )
}
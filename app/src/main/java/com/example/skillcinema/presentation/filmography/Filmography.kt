package com.example.skillcinema.presentation.film

import android.util.Log
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.skillcinema.data.model.Film
import com.example.skillcinema.domain.model.Movie


@Composable
fun Filmography(
    actorId: Int,
    viewModel: ActorViewModel= hiltViewModel(),
    navController: NavController
){

    var selectCategory by remember { mutableStateOf("ACTOR")}

    val filmographyState by viewModel.filmWithActor.collectAsState()
    val actorState by viewModel.actorState.collectAsState()

    LaunchedEffect(actorId) {
        viewModel.fetchActorById(actorId)
    }

    when(actorState){
        is ActorState.Loading-> CircularProgressIndicator(modifier = Modifier.size(10.dp))
        is ActorState.Success->{
            Column(modifier = Modifier.fillMaxSize().padding(top = 50.dp, start = 20.dp)) {

                Row(modifier = Modifier.fillMaxWidth().padding(start = 15.dp)){
                    IconButton(onClick = { navController.popBackStack() }) {

                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                    Spacer(modifier = Modifier.width(90.dp))
                    Text(text = "Фильмография")
                }
                Spacer(modifier = Modifier.height(20.dp))

                val actor = (actorState as ActorState.Success).actor
                val films = actor.films
                Text(text = actor.nameRu, fontSize = 24.sp)

                if(films?.isNotEmpty() == true && selectCategory.isEmpty()){
                    selectCategory = films.first().professionKey
                }
                Spacer(modifier = Modifier.height(30.dp))
                LazyRow(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                    item{
                        films?.map {it.professionKey}?.distinct()?.forEach{category ->
                            Button(
                                onClick = { selectCategory = category },
                                colors = ButtonDefaults
                                    .buttonColors(containerColor = if (selectCategory == category) Color.Blue else Color.Gray),
                            ){
                                Text(text = category)
                            }

                        }

                    }
                }
                Spacer(modifier = Modifier.height(30.dp))

                val filmsByCategory = filmographyState.filter { film->
                    films?.filter { it.filmId == film.kinopoiskId }
                        ?.map { it.professionKey }
                        ?.contains(selectCategory) ?: false

                }
                //mutableListOf<Movie>()
//                filmsByCategory.forEach { film ->
//                    films?.forEach{ withCategory->
//                        if(film.kinopoiskId == withCategory.filmId && withCategory.professionKey==selectCategory){
//                            filmsByCategory.add(film)
//                        }
//                    }
//                }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filmsByCategory) {film->
                        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center){
                            AsyncImage(model =film.posterUrl, contentDescription = null,
                                modifier = Modifier.width(96.dp).height(132.dp))
                            Spacer(modifier = Modifier.width(15.dp))
                            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){
                                film.nameRu?.let { Text(text = it, fontSize = 14.sp) }
                                Text(text = film.year.toString(), fontSize = 12.sp, color = Color.Gray)
                            }
                        }

                    }
                    item{ Spacer(modifier = Modifier.height(20.dp)) }
                }


            }

        }
        is ActorState.Error-> {
            val error = (actorState as ActorState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text=error)
            }

        }

        else -> {}
    }
}





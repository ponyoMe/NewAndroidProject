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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.skillcinema.presentation.film.ActorState
import com.example.skillcinema.presentation.film.ActorViewModel
import com.example.testing.R

@Composable
fun ActorScreen(actorId: Int, viewModel: ActorViewModel = hiltViewModel(), navController: NavController) {
    val actorState by viewModel.actorState.collectAsState()
    val actorWithFilms by viewModel.filmWithActor.collectAsState()

    LaunchedEffect(actorId) {
        println("fetching data with actor id : ${actorId}")
        viewModel.fetchActorById(actorId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        when (actorState) {
            is ActorState.Loading ->
                Box(modifier = Modifier.fillMaxSize().background(Color.LightGray)) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            is ActorState.Success -> {
                val actor = (actorState as ActorState.Success).actor

                Box(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
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
                                modifier = Modifier.size(24.dp),
                                tint = Color.Black
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        ) {
                        AsyncImage(
                            model = actor.posterUrl, contentDescription = null,
                            modifier = Modifier
                                .height(201.dp)
                                .width(146.dp)
                        )
                        Spacer(modifier = Modifier.width(25.dp))
                        Column {
                            Text(text = actor.nameRu, fontSize = 18.sp)
                            Text(text = actor.profession, fontSize = 14.sp, color = Color.Gray)
                        }
                    }
                    Spacer(modifier = Modifier.height(60.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Лучшее", fontSize = 18.sp)
                        Text(
                            text = "Все >", modifier = Modifier.clickable {
                                navController.navigate("filmography/${actorId}")
                            },
                            color = Color.Blue, fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(modifier = Modifier.padding(8.dp)) {
                        items(actorWithFilms.take(7)) { film ->
                            Column(modifier = Modifier.padding(8.dp)) {
                                AsyncImage(
                                    model = film.posterUrl, contentDescription = null,
                                    modifier = Modifier
                                        .height(156.dp)
                                        .width(111.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                film.nameRu?.let { Text(text = it, maxLines = 2, fontSize = 12.sp) }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .clickable {
                                navController.navigate("filmography/${actorId}")
                            }
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Фильмография", fontSize = 20.sp)
                        Text(text = "К списку>", color = Color.Blue, fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = actor.films?.size.toString(), fontSize = 10.sp, color = Color.Gray)
                }
            }
            is ActorState.Error -> {
                val error = (actorState as ActorState.Error).message
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: $error")
                }
            }
            else -> {}
        }
    }
}

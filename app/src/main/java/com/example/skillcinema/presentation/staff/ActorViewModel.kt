package com.example.skillcinema.presentation.film

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.skillcinema.data.model.Staff
import com.example.skillcinema.data.repository.MovieRepository
import com.example.skillcinema.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ActorState{
    object Loading: ActorState()
    data class Error(val message : String) :ActorState()
    data class Success( val actor : Staff) : ActorState()
}

@HiltViewModel
class ActorViewModel @Inject constructor(
    val repository: MovieRepository
) : ViewModel(){
    val _actorState= MutableStateFlow<ActorState>(ActorState.Loading)
    val actorState : StateFlow<ActorState> = _actorState

    val _filmWithActor = MutableStateFlow<List<Movie>>(emptyList())
    val filmWithActor : StateFlow<List<Movie>> = _filmWithActor

    fun fetchActorById(actorId : Int){
        viewModelScope.launch {
            _actorState.value = ActorState.Loading
            val result = repository.getStaffById(actorId)
            if(result.isSuccess){
                val staff = result.getOrNull()
                if(staff != null){
                    _actorState.value = ActorState.Success(staff)
                    getFilmById(staff.films?.take(7)?.map { it.filmId } ?: emptyList())
                }
            }else{
                val error = result.exceptionOrNull()?.message
                _actorState.value = ActorState.Error("${error}")
            }

        }
    }

    fun getFilmById(filmId : List<Int>){
        viewModelScope.launch {
            val films = filmId.mapNotNull { filmId->
                repository.getFilmById(filmId).getOrNull()

            }
            _filmWithActor.value = films
        }

    }
}


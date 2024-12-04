package com.example.skillcinema.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.skillcinema.data.dao.MovieDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dao : MovieDao
) : ViewModel(){
    private val _watchedState = MutableStateFlow(ProfileState())
    val watchedMovie : StateFlow<ProfileState> = _watchedState

    private val _favCount = MutableStateFlow(0)
    val favCount : StateFlow<Int> = _favCount

    private val _savedCount = MutableStateFlow(0)
    val savedCount : StateFlow<Int> =_savedCount

     init {
         getMovies()
         getFavCount()
         getSavedCount()
     }

    fun getMovies(){
        viewModelScope.launch {

            _watchedState.value = _watchedState.value.copy(Loading = true)

            try {
                val movies = dao.getAllMovie()
                _watchedState.value = _watchedState.value.copy(Loading = false, list = movies)

            }catch (e: HttpException){
                _watchedState.value =
                    e.message?.let { _watchedState.value.copy(Loading = false, Error = it) }!!
            }
        }
    }

    fun getFavCount(){
        viewModelScope.launch {
            _favCount.value = dao.getFavMovies()
        }
    }

    fun getSavedCount(){
        viewModelScope.launch {
            _savedCount.value = dao.getSavedMovies()
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            dao.deleteList()
            _watchedState.value = _watchedState.value.copy(Loading = false, list = emptyList())
        }
    }
}
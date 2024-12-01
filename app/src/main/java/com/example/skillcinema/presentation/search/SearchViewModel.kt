package com.example.skillcinema.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.domain.model.MovieList
import com.example.skillcinema.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel() {
    val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchState = MutableStateFlow<SearchState>(SearchState.Loading)
    val searchState = _searchState.asStateFlow()

    private var searchJob: Job? = null

    fun searchMovies() = viewModelScope.launch{
        _searchState.value = SearchState.Loading
        val query = searchQuery.value
        if (query.isNotEmpty()){
            try {
                val result = movieUseCase.getMoviesByKeyword(query)
                Log.d("searchMovies:", "MovieList: ${result.isEmpty()}")
                if(result.isNotEmpty()){
                    _searchState.value = SearchState.Success(result)
                } else {
                    _searchState.value = SearchState.Empty
                }
            } catch (e: Exception){
                _searchState.value = SearchState.Error("Error: ${e.message}")
            }
        }
    }

    fun onQueryTextChanged(query: String){
        _searchQuery.value = query

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(500)
            searchMovies()
        }
    }
}

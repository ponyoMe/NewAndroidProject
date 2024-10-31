//package com.example.testing
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class FilmViewModel(private val repository: CinemaRepository) : ViewModel() {
//
//    private val _popularFilms = MutableStateFlow<ScreenState<List<Film>>>(ScreenState.Initial)
//    val popularFilms: StateFlow<ScreenState<List<Film>>> = _popularFilms
//
//    private val _nowShowingFilms = MutableStateFlow<ScreenState<List<Film>>>(ScreenState.Initial)
//    val nowShowingFilms: StateFlow<ScreenState<List<Film>>> = _nowShowingFilms
//
//    private val _topRatedFilms = MutableStateFlow<ScreenState<List<Film>>>(ScreenState.Initial)
//    val topRatedFilms: StateFlow<ScreenState<List<Film>>> = _topRatedFilms
//
//    init {
//        fetchPopularFilms()
//        fetchNowShowingFilms()
//        fetchTopRatedFilms()
//    }
//
//    private fun fetchPopularFilms() {
//        _popularFilms.value = ScreenState.Loading
//        viewModelScope.launch {
//            _popularFilms.value = repository.getFilmsByCategory("popular")
//        }
//    }
//
//    private fun fetchNowShowingFilms() {
//        _nowShowingFilms.value = ScreenState.Loading
//        viewModelScope.launch {
//            _nowShowingFilms.value = repository.getFilmsByCategory("now_showing")
//        }
//    }
//
//    private fun fetchTopRatedFilms() {
//        _topRatedFilms.value = ScreenState.Loading
//        viewModelScope.launch {
//            _topRatedFilms.value = repository.getFilmsByCategory("top_rated")
//        }
//    }
//}

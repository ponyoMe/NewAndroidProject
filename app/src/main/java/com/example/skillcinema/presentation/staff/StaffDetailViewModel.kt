package com.example.skillcinema.presentation.staff


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillcinema.data.model.Staff
import com.example.skillcinema.data.repository.MovieRepository
import dagger.assisted.Assisted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



sealed class StaffDetailState {
    data object Loading : StaffDetailState()
    data class Success(val staffDetail: Staff) : StaffDetailState()
    data class Error(val message: String) : StaffDetailState()
}



class StaffDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _staffDetailState = MutableStateFlow<StaffDetailState>(StaffDetailState.Loading)
    val staffDetailState: StateFlow<StaffDetailState> get() = _staffDetailState

    init {
        var staffId = savedStateHandle.get<String>("staffId")?.toInt()
        if(staffId != null) {
            fetchStaffDetailById(staffId)
        }
    }



    fun fetchStaffDetailById(id:Int) =  viewModelScope.launch {
        _staffDetailState.value = StaffDetailState.Loading
        try {
            val result = repository.getStaffDetails(id)
            result.onSuccess { staff -> _staffDetailState.value =
                StaffDetailState.Success(staffDetail = staff)
            }.onFailure {
                error ->
                _staffDetailState.value = StaffDetailState.Error("Error occured ${error.message}")
            }

        }catch (e :Exception){
            _staffDetailState.value = StaffDetailState.Error("Error loading staff: ${e.message}")
        }
    }
}








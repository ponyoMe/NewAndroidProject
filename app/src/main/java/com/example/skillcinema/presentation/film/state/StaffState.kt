package com.example.skillcinema.presentation.film.state

import com.example.skillcinema.data.model.StaffResponse

sealed class StaffState {
    data object Loading: StaffState()
    data class Success(val staff: List<StaffResponse>) : StaffState()
    data class Error(val message: String) : StaffState()
}
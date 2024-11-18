package com.example.skillcinema.presentation.film

import com.example.skillcinema.data.model.StaffResponse
import com.example.skillcinema.domain.model.Movie

sealed class StaffState {
    data object Loading: StaffState()
    data class Success(val staff: List<StaffResponse>) : StaffState()
    data class Error(val message: String) : StaffState()
}
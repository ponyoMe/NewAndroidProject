package com.example.skillcinema.presentation.profile

import com.example.skillcinema.data.entity.DataClassForDb

data class ProfileState(
    val Loading : Boolean = false,
    val list : List<DataClassForDb> = emptyList(),
    val Error : String = ""
)

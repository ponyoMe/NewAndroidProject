package com.example.skillcinema.data.model

import kotlinx.serialization.Serializable

@Serializable
data class StaffResponse(
    val staffId: Int,
<<<<<<< HEAD
    val nameRu: String?,
    val nameEn: String?,
    val description: String?,
    val posterUrl: String?,
    val professionText: String?,
    val professionKey: String?
=======
    val nameRu: String,
    val nameEn: String,
    val description: String,
    val posterUrl: String?,
    val professionText: String,
    val professionKey: String
>>>>>>> refactor/project-structure
)
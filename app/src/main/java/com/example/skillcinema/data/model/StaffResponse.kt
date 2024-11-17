package com.example.skillcinema.data.model

import kotlinx.serialization.Serializable

@Serializable
data class StaffResponse(
    val staffId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val description: String?,
    val posterUrl: String?,
    val professionText: String?,
    val professionKey: String?
)
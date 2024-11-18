package com.example.skillcinema.data.model

data class Staff(
    val personId : Int,
    val webUrl: String,
    val nameRu: String,
    val nameEn: String,
    val sex: String,
    val posterUrl: String,
    val growth : String,
    val birthday: String,
    val death: String?,
    val age: Int,
    val birthplace: String?,
    val deathplace: String?,
    val hasAwards: Int,
    val profession: String,
    val facts: List<String>?,
    val films: List<Film>?
)






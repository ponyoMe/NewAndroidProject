package com.example.skillcinema.core

fun interface BaseMapper<FROM, TO> {
    fun map(source: FROM): TO
}
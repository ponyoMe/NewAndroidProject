package com.example.skillcinema.core


interface BaseRepository {

    suspend fun <T> safeApiCall(result: suspend () -> T): Result<T> =
        runCatching { result.invoke() }
            .onSuccess { Result.success(result) }
            .onFailure { Result.failure<Throwable>(it) }

    suspend fun <FROM, TO> mappedSafeApiCall(
        mapper: BaseMapper<FROM, TO>,
        result: suspend () -> FROM
    ): Result<TO> =
        runCatching { result.invoke().let { mapper.map(it) } }
            .onSuccess { Result.success(result) }
            .onFailure { Result.failure<Throwable>(it) }
}
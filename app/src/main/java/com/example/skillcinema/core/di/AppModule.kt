package com.example.skillcinema.core.di

import android.content.Context
import androidx.room.Room
import com.example.skillcinema.data.client.KinopoiskApi
import com.example.skillcinema.data.repository.MovieRepository
import com.example.skillcinema.data.dao.MovieDao
import com.example.skillcinema.data.database.MovieDatabase
import com.example.skillcinema.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideKinopoiskApi(retrofit: Retrofit): KinopoiskApi {
        return retrofit.create(KinopoiskApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        kinopoiskApi: KinopoiskApi,
        dao : MovieDao
    ): MovieRepository {
        return MovieRepository(kinopoiskApi, dao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context : Context) : MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            name = "database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(database: MovieDatabase): MovieDao {
        return database.dao()
    }

}
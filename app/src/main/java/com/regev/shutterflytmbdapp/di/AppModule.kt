package com.regev.shutterflytmbdapp.di

import com.regev.shutterflytmbdapp.network.TmbdApiService
import com.regev.shutterflytmbdapp.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTmbdApiService(): TmbdApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmbdApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(tmbdApiService: TmbdApiService): MovieRepository {
        return MovieRepository(tmbdApiService)
    }
}
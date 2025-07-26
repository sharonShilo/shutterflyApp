package com.regev.shutterflytmbdapp.repository

import com.regev.shutterflytmbdapp.model.Genre
import com.regev.shutterflytmbdapp.network.TmbdApiService
import javax.inject.Inject
import com.regev.shutterflytmbdapp.BuildConfig
import com.regev.shutterflytmbdapp.model.Movie

class MovieRepository @Inject constructor(
    private val apiService: TmbdApiService
) {
    private val apiKey = BuildConfig.API_KEY

    suspend fun getMovieGenres(): List<Genre> {
        return apiService.getMovieGenres(apiKey).genres
    }

    suspend fun getMovieByGenre(genreID: Int): List<Movie> {
        return apiService.getMoviesByGenre(apiKey, genreID).results
    }

}
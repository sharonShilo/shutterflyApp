package com.regev.shutterflytmbdapp.network

import com.regev.shutterflytmbdapp.model.GenreResponse
import com.regev.shutterflytmbdapp.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmbdApiService {

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String,
    ): GenreResponse

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int,
        @Query("language") language: String = "en-US"
    ): MovieResponse


}
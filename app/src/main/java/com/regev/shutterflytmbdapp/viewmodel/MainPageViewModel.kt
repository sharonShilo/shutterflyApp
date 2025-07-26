package com.regev.shutterflytmbdapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.regev.shutterflytmbdapp.model.Genre
import com.regev.shutterflytmbdapp.model.Movie
import com.regev.shutterflytmbdapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieGenres = MutableStateFlow<List<Genre>>(emptyList())
    val movieGenres: StateFlow<List<Genre>> = _movieGenres

    private val _moviesForSelectedGenre = MutableStateFlow<List<Movie>>(emptyList())
    val moviesForSelectedGenre : StateFlow<List<Movie>> = _moviesForSelectedGenre

    private val _selectedGenre = MutableStateFlow<Genre?>(null)
    val selectedGenre: StateFlow<Genre?> = _selectedGenre

    init {
        getMovieGenres()
    }

    private fun getMovieGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _movieGenres.value = movieRepository.getMovieGenres()
                if (movieGenres.value.isNotEmpty()) {
                    _selectedGenre.value = movieGenres.value[0]
                    getMovieByGenre(movieGenres.value[0].id)
                }
            } catch (e: Exception) {
                Log.e("MainPageViewModel", "retrieve data error $e")
            }
        }
    }

    fun onGenreSelected(genre: Genre) {
        _selectedGenre.value = genre
        getMovieByGenre(genre.id)
    }

    private fun getMovieByGenre(genreId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _moviesForSelectedGenre.value = movieRepository.getMovieByGenre(genreId)
            } catch (e: Exception) {
                Log.e("MainPageViewModel", "retrieve data error $e")
            }
        }
    }

}
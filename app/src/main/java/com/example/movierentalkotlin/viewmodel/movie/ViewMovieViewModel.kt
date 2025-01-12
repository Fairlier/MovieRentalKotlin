package com.example.movierentalkotlin.viewmodel.movie

import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.MovieDao

class ViewMovieViewModel(id: Long, val dao: MovieDao) : ViewModel() {

    val movie = dao.getById(id)
}
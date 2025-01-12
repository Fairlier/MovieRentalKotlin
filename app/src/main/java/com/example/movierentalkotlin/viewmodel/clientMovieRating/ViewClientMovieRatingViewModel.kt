package com.example.movierentalkotlin.viewmodel.clientMovieRating

import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.database.dao.ClientMovieRatingDao
import com.example.movierentalkotlin.database.dao.MovieDao

class ViewClientMovieRatingViewModel(val id: Long,
                                     val clientMovieRatingDao: ClientMovieRatingDao,
                                     val clientDao: ClientDao,
                                     val movieDao: MovieDao) : ViewModel() {

    val clientMovieRatingWithDetailsDto = clientMovieRatingDao.getByIdWithDetails(id)
}
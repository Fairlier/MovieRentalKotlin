package com.example.movierentalkotlin.viewmodel.clientMovieRating

import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.ClientMovieRatingDao

class ViewClientMovieRatingViewModel(val id: Long,
                                     val clientMovieRatingDao: ClientMovieRatingDao) : ViewModel() {

    val clientMovieRatingWithDetailsDto = clientMovieRatingDao.getByIdWithDetails(id)
}
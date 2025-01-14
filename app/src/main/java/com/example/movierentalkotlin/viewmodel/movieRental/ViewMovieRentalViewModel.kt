package com.example.movierentalkotlin.viewmodel.movieRental

import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.ClientMovieRatingDao

class ViewMovieRentalViewModel(val id: Long,
                               val clientMovieRatingDao: ClientMovieRatingDao) : ViewModel() {

    val clientMovieRatingWithDetailsDto = clientMovieRatingDao.getByIdWithDetails(id)
}
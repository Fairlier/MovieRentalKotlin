package com.example.movierentalkotlin.viewmodel.movieRental

import androidx.lifecycle.ViewModel
import com.example.movierentalkotlin.database.dao.MovieRentalDao

class ViewMovieRentalViewModel(val id: Long,
                               val movieRentalDao: MovieRentalDao) : ViewModel() {

    val movieRentalWithDetailsDto = movieRentalDao.getByIdWithDetails(id)
}
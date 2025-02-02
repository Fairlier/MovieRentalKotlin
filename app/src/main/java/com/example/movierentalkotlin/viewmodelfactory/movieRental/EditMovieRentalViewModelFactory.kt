package com.example.movierentalkotlin.viewmodelfactory.movieRental

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movierentalkotlin.database.dao.ClientDao
import com.example.movierentalkotlin.database.dao.EmployeeDao
import com.example.movierentalkotlin.database.dao.MovieDao
import com.example.movierentalkotlin.database.dao.MovieRentalDao
import com.example.movierentalkotlin.viewmodel.movieRental.EditMovieRentalViewModel

class EditMovieRentalViewModelFactory(private val id: Long,
                                      private val movieRentalDao: MovieRentalDao,
                                      private val clientDao: ClientDao,
                                      private val employeeDao: EmployeeDao,
                                      private val movieDao: MovieDao)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditMovieRentalViewModel::class.java)) {
            return EditMovieRentalViewModel(id, movieRentalDao, clientDao, employeeDao, movieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
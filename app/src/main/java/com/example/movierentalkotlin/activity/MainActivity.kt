package com.example.movierentalkotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.database.entity.Client
import com.example.movierentalkotlin.database.entity.ClientMovieRating
import com.example.movierentalkotlin.database.entity.Employee
import com.example.movierentalkotlin.database.entity.Movie
import com.example.movierentalkotlin.database.entity.MovieRental
import com.example.movierentalkotlin.databinding.ActivityMainBinding
import com.example.movierentalkotlin.util.SessionManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.navHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController

        setupStartDestination(navController)

        NavigationUI.setupWithNavController(binding.navView, navController)

        binding.navView.setNavigationItemSelectedListener  { item ->
            when (item.itemId) {
                R.id.authorizationFragment -> {
                    binding.drawerLayout.closeDrawers()
                    handleLogout(navController)
                    true
                }
                else -> {
                    binding.drawerLayout.closeDrawers()
                    navController.navigate(item.itemId)
                    true
                }
            }
        }
    }

    private fun setupStartDestination(navController: NavController) {
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
        graph.setStartDestination(
            if (sessionManager.isLoggedIn()) R.id.movieCatalogFragment else R.id.authorizationFragment
        )
        navController.graph = graph
    }

    private fun handleLogout(navController: NavController) {
        sessionManager.logout()

        if (navController.currentDestination?.id != R.id.authorizationFragment) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.nav_graph, inclusive = true)
                .build()

            navController.navigate(R.id.authorizationFragment, null, navOptions)
        }
    }
}
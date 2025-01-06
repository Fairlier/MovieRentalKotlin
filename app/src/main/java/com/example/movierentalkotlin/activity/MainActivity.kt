package com.example.movierentalkotlin.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.databinding.ActivityMainBinding
import com.example.movierentalkotlin.viewmodel.AuthorizationViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.navHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController

        setupStartDestination(navController)

        NavigationUI.setupWithNavController(binding.navView, navController)

        viewModel.isLoggedIn.observe(this) {
            setupStartDestination(navController)
        }
    }

    private fun setupStartDestination(navController: NavController) {
        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val isLoggedIn  = sharedPreferences.getBoolean("isLoggedIn", false)
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
        val startDestination = if (isLoggedIn) { // TODO
            R.id.movieCatalogFragment
        } else {
            R.id.authorizationFragment
        }
        graph.setStartDestination(startDestination)
        navController.setGraph(graph, null)
    }
}
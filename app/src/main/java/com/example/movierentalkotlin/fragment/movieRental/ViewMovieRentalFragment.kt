package com.example.movierentalkotlin.fragment.movieRental

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.activity.MainActivity
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentViewMovieRentalBinding
import com.example.movierentalkotlin.viewmodel.movieRental.ViewMovieRentalViewModel
import com.example.movierentalkotlin.viewmodelfactory.movieRental.ViewMovieRentalViewModelFactory

class ViewMovieRentalFragment : Fragment() {

    private var _binding: FragmentViewMovieRentalBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewMovieRentalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dao = MovieRentalDatabase.getInstance(application).movieRentalDao

        val id = ViewMovieRentalFragmentArgs.fromBundle(requireArguments()).id
        val viewModelFactory = ViewMovieRentalViewModelFactory(id, dao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[ViewMovieRentalViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar_view_movie_rental, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.editMovieRentalFragment -> {
                        val currentId = ViewMovieRentalFragmentArgs.fromBundle(requireArguments()).id
                        val action =
                            ViewMovieRentalFragmentDirections
                                .actionViewMovieRentalFragmentToEditMovieRentalFragment(currentId)
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.menuToolbar)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.movieRentalCatalogFragment),
            (requireActivity() as MainActivity).binding.drawerLayout
        )
        binding.menuToolbar.setupWithNavController(navController, appBarConfiguration)

        binding.menuToolbar.setNavigationOnClickListener {
            navController.navigate(
                ViewMovieRentalFragmentDirections
                .actionViewMovieRentalFragmentToMovieRentalCatalogFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.movierentalkotlin.fragment

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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.activity.MainActivity
import com.example.movierentalkotlin.adapter.MovieCatalogItemAdapter
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentMovieCatalogBinding
import com.example.movierentalkotlin.viewmodel.MovieCatalogViewModel
import com.example.movierentalkotlin.viewmodel.SharedViewModel
import com.example.movierentalkotlin.viewmodelfactory.MovieCatalogViewModelFactory

class MovieCatalogFragment : Fragment() {

    private var _binding: FragmentMovieCatalogBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dao = MovieRentalDatabase.getInstance(application).movieDao

        val viewModelFactory = MovieCatalogViewModelFactory(dao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[MovieCatalogViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar_movie_catalog, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.searchMovieFragment -> {
                        val action = MovieCatalogFragmentDirections
                            .actionMovieCatalogFragmentToSearchMovieFragment()
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
            setOf(R.id.movieCatalogFragment),
            (requireActivity() as MainActivity).binding.drawerLayout
        )
        binding.menuToolbar.setupWithNavController(navController, appBarConfiguration)

        val adapter = MovieCatalogItemAdapter{ id ->
            viewModel.onCatalogItemClicked(id)
        }
        binding.movieCatalog.adapter = adapter

        sharedViewModel.movieFilters.observe(viewLifecycleOwner) { filters ->
            viewModel.setFilters(filters)
        }

        viewModel.catalog.observe(viewLifecycleOwner) { items ->
            items?.let {
                adapter.submitList(items)
            }
        }

        viewModel.navigateToView.observe(viewLifecycleOwner) { id ->
            id?.let {
                val action = MovieCatalogFragmentDirections
                    .actionMovieCatalogFragmentToViewMovieFragment(id)
                this.findNavController().navigate(action)
                viewModel.onCatalogItemNavigated()
            }
        }

        viewModel.navigateToInsert.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                val action = MovieCatalogFragmentDirections
                    .actionMovieCatalogFragmentToInsertMovieFragment()
                this.findNavController().navigate(action)
                viewModel.onNavigatedToInsert()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
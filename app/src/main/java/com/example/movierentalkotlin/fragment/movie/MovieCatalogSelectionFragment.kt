package com.example.movierentalkotlin.fragment.movie

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
import com.example.movierentalkotlin.databinding.FragmentMovieCatalogSelectionBinding
import com.example.movierentalkotlin.fragment.client.ClientCatalogSelectionFragmentDirections
import com.example.movierentalkotlin.util.Constants
import com.example.movierentalkotlin.viewmodel.movie.MovieCatalogSelectionViewModel
import com.example.movierentalkotlin.viewmodel.SharedViewModel
import com.example.movierentalkotlin.viewmodelfactory.movie.MovieCatalogSelectionViewModelFactory

class MovieCatalogSelectionFragment : Fragment() {

    private var _binding: FragmentMovieCatalogSelectionBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieCatalogSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dao = MovieRentalDatabase.getInstance(application).movieDao

        val viewModelFactory = MovieCatalogSelectionViewModelFactory(dao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[MovieCatalogSelectionViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar_movie_catalog, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.searchMovieFragment -> {
                        val action =
                            MovieCatalogSelectionFragmentDirections
                                .actionMovieCatalogSelectionFragmentToSearchMovieSelectionFragment()
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
            setOf(
                R.id.clientMovieRatingCatalogFragment,
                R.id.movieRentalCatalogFragment
            ),
            (requireActivity() as MainActivity).binding.drawerLayout
        )
        binding.menuToolbar.setupWithNavController(navController, appBarConfiguration)

        binding.menuToolbar.setNavigationOnClickListener {
            this.findNavController().navigateUp()
        }

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

        viewModel.navigateToBack.observe(viewLifecycleOwner) { id ->
            id?.let {
                when (sharedViewModel.sourceFragment.value) {
                    Constants.FragmentSource.INSERT_CLIENT_MOVIE_RATING -> {
                        sharedViewModel.setSelectedMovieId(id)
                        val action =
                            MovieCatalogSelectionFragmentDirections
                                .actionMovieCatalogSelectionFragmentToInsertClientMovieRatingFragment()
                        this.findNavController().navigate(action)
                        viewModel.onCatalogItemNavigated()
                    }
                    Constants.FragmentSource.SEARCH_CLIENT_MOVIE_RATING -> {
                        sharedViewModel.setSelectedMovieId(id)
                        val action =
                            MovieCatalogSelectionFragmentDirections
                                .actionMovieCatalogSelectionFragmentToSearchClientMovieRatingFragment()
                        this.findNavController().navigate(action)
                        viewModel.onCatalogItemNavigated()
                    }
                    Constants.FragmentSource.EDIT_CLIENT_MOVIE_RATING -> {
                        sharedViewModel.setSelectedMovieId(id)
                        sharedViewModel.currentIdForEditClientMovieRating?.let { currentIdForEditClientMovieRating ->
                            val action =
                                MovieCatalogSelectionFragmentDirections
                                    .actionMovieCatalogSelectionFragmentToEditClientMovieRatingFragment(currentIdForEditClientMovieRating)
                            findNavController().navigate(action)
                            viewModel.onCatalogItemNavigated()
                        }
                    }
                    Constants.FragmentSource.INSERT_MOVIE_RENTAL -> {
                        sharedViewModel.setSelectedMovieId(id)
                        val action =
                            MovieCatalogSelectionFragmentDirections
                                .actionMovieCatalogSelectionFragmentToInsertMovieRentalFragment()
                        this.findNavController().navigate(action)
                        viewModel.onCatalogItemNavigated()
                    }
                    Constants.FragmentSource.SEARCH_MOVIE_RENTAL -> {
                        sharedViewModel.setSelectedMovieId(id)
                        val action =
                            MovieCatalogSelectionFragmentDirections
                                .actionMovieCatalogSelectionFragmentToSearchMovieRentalFragment()
                        this.findNavController().navigate(action)
                        viewModel.onCatalogItemNavigated()
                    }
                    Constants.FragmentSource.EDIT_MOVIE_RENTAL -> {
                        sharedViewModel.setSelectedMovieId(id)
                        sharedViewModel.currentIdForEditMovieRental?.let { currentIdForEditMovieRental ->
                            val action =
                                MovieCatalogSelectionFragmentDirections
                                    .actionMovieCatalogSelectionFragmentToEditMovieRentalFragment(currentIdForEditMovieRental)
                            findNavController().navigate(action)
                            viewModel.onCatalogItemNavigated()
                        }
                    }
                    else -> {
                        this.findNavController().navigateUp()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.movierentalkotlin.fragment.clientMovieRating

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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.activity.MainActivity
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentSearchClientMovieRatingBinding
import com.example.movierentalkotlin.util.ClientMovieRatingData
import com.example.movierentalkotlin.util.Constants
import com.example.movierentalkotlin.viewmodel.SharedViewModel
import com.example.movierentalkotlin.viewmodel.clientMovieRating.SearchClientMovieRatingViewModel
import com.example.movierentalkotlin.viewmodelfactory.clientMovieRating.SearchClientMovieRatingViewModelFactory

class SearchClientMovieRatingFragment : Fragment() {

    private var _binding: FragmentSearchClientMovieRatingBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchClientMovieRatingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val clientMovieRatingDao = MovieRentalDatabase.getInstance(application).clientMovieRatingDao
        val clientDao = MovieRentalDatabase.getInstance(application).clientDao
        val movieDao = MovieRentalDatabase.getInstance(application).movieDao

        val viewModelFactory = SearchClientMovieRatingViewModelFactory(clientMovieRatingDao, clientDao, movieDao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[SearchClientMovieRatingViewModel::class.java]

        viewModel.initializationClientMovieRatingData(sharedViewModel.clientMovieRatingData)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.menuToolbar)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.clientMovieRatingCatalogFragment),
            (requireActivity() as MainActivity).binding.drawerLayout
        )
        binding.menuToolbar.setupWithNavController(navController, appBarConfiguration)

        binding.menuToolbar.setNavigationOnClickListener {
            sharedViewModel.clearSelectedClientId()
            sharedViewModel.clearSelectedMovieId()
            sharedViewModel.clearSourceFragment()
            sharedViewModel.clearClientMovieRatingData()
            navController.navigate(SearchClientMovieRatingFragmentDirections
                .actionSearchClientMovieRatingFragmentToClientMovieRatingCatalogFragment())
        }

        sharedViewModel.selectedClientId.observe(viewLifecycleOwner) { id ->
            id?.let {
                viewModel.updateClient(id)
                sharedViewModel.initializationClientMovieRatingData(
                    viewModel.clientMovieRatingData.value ?: ClientMovieRatingData()
                )
                sharedViewModel.clearSelectedClientId()
                sharedViewModel.clearSourceFragment()
            }
        }

        sharedViewModel.selectedMovieId.observe(viewLifecycleOwner) { id ->
            id?.let {
                viewModel.updateMovie(id)
                sharedViewModel.initializationClientMovieRatingData(
                    viewModel.clientMovieRatingData.value ?: ClientMovieRatingData()
                )
                sharedViewModel.clearSelectedMovieId()
                sharedViewModel.clearSourceFragment()
            }
        }

        viewModel.navigateToClientCatalogSelection.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                sharedViewModel.setSourceFragment(Constants.FragmentSource.SEARCH_CLIENT_MOVIE_RATING)
                sharedViewModel.initializationClientMovieRatingData(
                    viewModel.clientMovieRatingData.value ?: ClientMovieRatingData()
                )
                val action =
                    SearchClientMovieRatingFragmentDirections
                        .actionSearchClientMovieRatingFragmentToClientCatalogSelectionFragment()
                view.findNavController().navigate(action)
                viewModel.onClientCardNavigated()
            }
        }

        viewModel.navigateToMovieCatalogSelection.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                sharedViewModel.setSourceFragment(Constants.FragmentSource.SEARCH_CLIENT_MOVIE_RATING)
                sharedViewModel.initializationClientMovieRatingData(
                    viewModel.clientMovieRatingData.value ?: ClientMovieRatingData()
                )
                val action =
                    SearchClientMovieRatingFragmentDirections
                        .actionSearchClientMovieRatingFragmentToMovieCatalogSelectionFragment()
                view.findNavController().navigate(action)
                viewModel.onMovieCardNavigated()
            }
        }

        viewModel.navigateToCatalogAfterSearch.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                sharedViewModel.clearSelectedClientId()
                sharedViewModel.clearSelectedMovieId()
                sharedViewModel.clearSourceFragment()
                sharedViewModel.clearClientMovieRatingData()
                viewModel.filters.value?.let { sharedViewModel.setClientMovieRatingFilters(it) }
                val action =
                    SearchClientMovieRatingFragmentDirections
                        .actionSearchClientMovieRatingFragmentToClientMovieRatingCatalogFragment()
                view.findNavController().navigate(action)
                viewModel.onNavigatedToCatalogAfterSearch()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
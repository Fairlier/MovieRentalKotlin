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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.activity.MainActivity
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentSearchMovieRentalBinding
import com.example.movierentalkotlin.util.Constants
import com.example.movierentalkotlin.util.MovieRentalData
import com.example.movierentalkotlin.viewmodel.SharedViewModel
import com.example.movierentalkotlin.viewmodel.movieRental.SearchMovieRentalViewModel
import com.example.movierentalkotlin.viewmodelfactory.movieRental.SearchMovieRentalViewModelFactory

class SearchMovieRentalFragment : Fragment() {

    private var _binding: FragmentSearchMovieRentalBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchMovieRentalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val movieRentalDao = MovieRentalDatabase.getInstance(application).movieRentalDao
        val clientDao = MovieRentalDatabase.getInstance(application).clientDao
        val employeeDao = MovieRentalDatabase.getInstance(application).employeeDao
        val movieDao = MovieRentalDatabase.getInstance(application).movieDao

        val viewModelFactory = SearchMovieRentalViewModelFactory(movieRentalDao, clientDao, employeeDao, movieDao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[SearchMovieRentalViewModel::class.java]

        viewModel.initializationMovieRentalData(sharedViewModel.movieRentalData)

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
            setOf(R.id.movieRentalCatalogFragment),
            (requireActivity() as MainActivity).binding.drawerLayout
        )
        binding.menuToolbar.setupWithNavController(navController, appBarConfiguration)

        binding.menuToolbar.setNavigationOnClickListener {
            sharedViewModel.clearSelectedClientId()
            sharedViewModel.clearSelectedEmployeeId()
            sharedViewModel.clearSelectedMovieId()
            sharedViewModel.clearSourceFragment()
            sharedViewModel.clearMovieRentalData()
            navController.navigate(
                SearchMovieRentalFragmentDirections
                .actionSearchMovieRentalFragmentToMovieRentalCatalogFragment())
        }

        sharedViewModel.selectedClientId.observe(viewLifecycleOwner) { id ->
            id?.let {
                viewModel.updateClient(id)
                sharedViewModel.initializationMovieRentalData(
                    viewModel.movieRentalData.value ?: MovieRentalData()
                )
                sharedViewModel.clearSelectedClientId()
                sharedViewModel.clearSourceFragment()
            }
        }

        sharedViewModel.selectedEmployeeId.observe(viewLifecycleOwner) { id ->
            id?.let {
                viewModel.updateEmployee(id)
                sharedViewModel.initializationMovieRentalData(
                    viewModel.movieRentalData.value ?: MovieRentalData()
                )
                sharedViewModel.clearSelectedEmployeeId()
                sharedViewModel.clearSourceFragment()
            }
        }

        sharedViewModel.selectedMovieId.observe(viewLifecycleOwner) { id ->
            id?.let {
                viewModel.updateMovie(id)
                sharedViewModel.initializationMovieRentalData(
                    viewModel.movieRentalData.value ?: MovieRentalData()
                )
                sharedViewModel.clearSelectedMovieId()
                sharedViewModel.clearSourceFragment()
            }
        }

        viewModel.navigateToClientCatalogSelection.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                sharedViewModel.setSourceFragment(Constants.FragmentSource.SEARCH_MOVIE_RENTAL)
                sharedViewModel.initializationMovieRentalData(
                    viewModel.movieRentalData.value ?: MovieRentalData()
                )
                val action = SearchMovieRentalFragmentDirections
                    .actionSearchMovieRentalFragmentToClientCatalogSelectionFragment()
                view.findNavController().navigate(action)
                viewModel.onClientCardNavigated()
            }
        }

        viewModel.navigateToEmployeeCatalogSelection.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                sharedViewModel.setSourceFragment(Constants.FragmentSource.SEARCH_MOVIE_RENTAL)
                sharedViewModel.initializationMovieRentalData(
                    viewModel.movieRentalData.value ?: MovieRentalData()
                )
                val action = InsertMovieRentalFragmentDirections
                    .actionInsertMovieRentalFragmentToEmployeeCatalogSelectionFragment()
                view.findNavController().navigate(action)
                viewModel.onEmployeeCardNavigated()
            }
        }

        viewModel.navigateToMovieCatalogSelection.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                sharedViewModel.setSourceFragment(Constants.FragmentSource.SEARCH_MOVIE_RENTAL)
                sharedViewModel.initializationMovieRentalData(
                    viewModel.movieRentalData.value ?: MovieRentalData()
                )
                val action = SearchMovieRentalFragmentDirections
                    .actionSearchMovieRentalFragmentToMovieCatalogSelectionFragment()
                view.findNavController().navigate(action)
                viewModel.onMovieCardNavigated()
            }
        }

        viewModel.navigateToCatalogAfterSearch.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                sharedViewModel.clearSelectedClientId()
                sharedViewModel.clearSelectedEmployeeId()
                sharedViewModel.clearSelectedMovieId()
                sharedViewModel.clearSourceFragment()
                sharedViewModel.clearMovieRentalData()
                viewModel.filters.value?.let { sharedViewModel.setMovieRentalFilters(it) }
                val action =
                    SearchMovieRentalFragmentDirections
                        .actionSearchMovieRentalFragmentToMovieRentalCatalogFragment()
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
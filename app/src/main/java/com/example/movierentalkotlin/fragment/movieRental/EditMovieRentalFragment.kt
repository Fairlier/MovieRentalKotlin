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
import com.example.movierentalkotlin.databinding.FragmentEditMovieRentalBinding
import com.example.movierentalkotlin.util.ClientMovieRatingData
import com.example.movierentalkotlin.util.Constants
import com.example.movierentalkotlin.util.MovieRentalData
import com.example.movierentalkotlin.viewmodel.SharedViewModel
import com.example.movierentalkotlin.viewmodel.movieRental.EditMovieRentalViewModel
import com.example.movierentalkotlin.viewmodelfactory.movieRental.EditMovieRentalViewModelFactory

class EditMovieRentalFragment : Fragment() {

    private var _binding: FragmentEditMovieRentalBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditMovieRentalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val movieRentalDao = MovieRentalDatabase.getInstance(application).movieRentalDao
        val clientDao = MovieRentalDatabase.getInstance(application).clientDao
        val employeeDao = MovieRentalDatabase.getInstance(application).employeeDao
        val movieDao = MovieRentalDatabase.getInstance(application).movieDao

        val id = EditMovieRentalFragmentArgs.fromBundle(requireArguments()).id
        val viewModelFactory = EditMovieRentalViewModelFactory(id, movieRentalDao, clientDao, employeeDao, movieDao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[EditMovieRentalViewModel::class.java]

        sharedViewModel.currentIdForEditMovieRental = id
        viewModel.initializationMovieRentalData(sharedViewModel.movieRentalData)
        viewModel.updateMovieRentalDataFromDto()

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
            sharedViewModel.clearSelectedEmployeeId()
            sharedViewModel.clearSelectedMovieId()
            sharedViewModel.clearSourceFragment()
            sharedViewModel.clearMovieRentalData()
            sharedViewModel.clearCurrentIdForEditMovieRental()
            navController.navigate(
                EditMovieRentalFragmentDirections
                .actionEditMovieRentalFragmentToViewMovieRentalFragment(id))
        }

        sharedViewModel.selectedClientId.observe(viewLifecycleOwner) { clientId ->
            clientId?.let {
                viewModel.updateClient(clientId)
                sharedViewModel.initializationMovieRentalData(
                    viewModel.movieRentalData.value ?: MovieRentalData()
                )
                sharedViewModel.clearSelectedClientId()
                sharedViewModel.clearSourceFragment()
            }
        }

        sharedViewModel.selectedEmployeeId.observe(viewLifecycleOwner) { employeeId ->
            employeeId?.let {
                viewModel.updateEmployee(employeeId)
                sharedViewModel.initializationMovieRentalData(
                    viewModel.movieRentalData.value ?: MovieRentalData()
                )
                sharedViewModel.clearSelectedEmployeeId()
                sharedViewModel.clearSourceFragment()
            }
        }

        sharedViewModel.selectedMovieId.observe(viewLifecycleOwner) { movieId ->
            movieId?.let {
                viewModel.updateMovie(movieId)
                sharedViewModel.initializationMovieRentalData(
                    viewModel.movieRentalData.value ?: MovieRentalData()
                )
                sharedViewModel.clearSelectedMovieId()
                sharedViewModel.clearSourceFragment()
            }
        }

        viewModel.navigateToClientCatalogSelection.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                sharedViewModel.setSourceFragment(Constants.FragmentSource.EDIT_MOVIE_RENTAL)
                sharedViewModel.initializationMovieRentalData(
                    viewModel.movieRentalData.value ?: MovieRentalData()
                )
                val action = EditMovieRentalFragmentDirections
                    .actionEditMovieRentalFragmentToClientCatalogSelectionFragment()
                view.findNavController().navigate(action)
                viewModel.onClientCardNavigated()
            }
        }

        viewModel.navigateToEmployeeCatalogSelection.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                sharedViewModel.setSourceFragment(Constants.FragmentSource.EDIT_MOVIE_RENTAL)
                sharedViewModel.initializationMovieRentalData(
                    viewModel.movieRentalData.value ?: MovieRentalData()
                )
                val action = EditMovieRentalFragmentDirections
                    .actionEditMovieRentalFragmentToEmployeeCatalogSelectionFragment()
                view.findNavController().navigate(action)
                viewModel.onEmployeeCardNavigated()
            }
        }

        viewModel.navigateToMovieCatalogSelection.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                sharedViewModel.setSourceFragment(Constants.FragmentSource.EDIT_MOVIE_RENTAL)
                sharedViewModel.initializationMovieRentalData(
                    viewModel.movieRentalData.value ?: MovieRentalData()
                )
                val action = EditMovieRentalFragmentDirections
                    .actionEditMovieRentalFragmentToMovieCatalogSelectionFragment()
                view.findNavController().navigate(action)
                viewModel.onMovieCardNavigated()
            }
        }

        viewModel.navigateToViewAfterUpdate.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                sharedViewModel.clearSelectedClientId()
                sharedViewModel.clearSelectedEmployeeId()
                sharedViewModel.clearSelectedMovieId()
                sharedViewModel.clearSourceFragment()
                sharedViewModel.clearMovieRentalData()
                sharedViewModel.clearCurrentIdForEditMovieRental()
                val action = EditMovieRentalFragmentDirections
                    .actionEditMovieRentalFragmentToViewMovieRentalFragment(id)
                view.findNavController().navigate(action)
                viewModel.onNavigatedToViewAfterUpdate()
            }
        }

        viewModel.navigateToViewAfterDelete.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                sharedViewModel.clearSelectedClientId()
                sharedViewModel.clearSelectedEmployeeId()
                sharedViewModel.clearSelectedMovieId()
                sharedViewModel.clearSourceFragment()
                sharedViewModel.clearMovieRentalData()
                sharedViewModel.clearCurrentIdForEditMovieRental()
                val action = EditMovieRentalFragmentDirections
                    .actionEditMovieRentalFragmentToMovieRentalCatalogFragment()
                view.findNavController().navigate(action)
                viewModel.onNavigatedToViewAfterDelete()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
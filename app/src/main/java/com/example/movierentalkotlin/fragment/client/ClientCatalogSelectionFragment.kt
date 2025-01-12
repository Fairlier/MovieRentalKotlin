package com.example.movierentalkotlin.fragment.client

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
import com.example.movierentalkotlin.adapter.ClientCatalogItemAdapter
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentClientCatalogSelectionBinding
import com.example.movierentalkotlin.util.Constants
import com.example.movierentalkotlin.viewmodel.client.ClientCatalogSelectionViewModel
import com.example.movierentalkotlin.viewmodel.SharedViewModel
import com.example.movierentalkotlin.viewmodelfactory.client.ClientCatalogSelectionViewModelFactory

class ClientCatalogSelectionFragment : Fragment() {

    private var _binding: FragmentClientCatalogSelectionBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClientCatalogSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dao = MovieRentalDatabase.getInstance(application).clientDao

        val viewModelFactory = ClientCatalogSelectionViewModelFactory(dao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[ClientCatalogSelectionViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar_client_catalog, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.searchClientFragment -> {
                        val action =
                            ClientCatalogSelectionFragmentDirections.actionClientCatalogSelectionFragmentToSearchClientSelectionFragment()
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
            setOf(R.id.clientMovieRatingCatalogFragment),
            (requireActivity() as MainActivity).binding.drawerLayout
        )
        binding.menuToolbar.setupWithNavController(navController, appBarConfiguration)

        binding.menuToolbar.setNavigationOnClickListener {
            navController.navigate(ClientCatalogSelectionFragmentDirections
                .actionClientCatalogSelectionFragmentToInsertClientMovieRatingFragment())
        }

        val adapter = ClientCatalogItemAdapter{ id ->
            viewModel.onCatalogItemClicked(id)
        }
        binding.clientCatalog.adapter = adapter

        sharedViewModel.clientFilters.observe(viewLifecycleOwner) { filters ->
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
                        sharedViewModel.setSelectedClientId(id)
                        val action = ClientCatalogSelectionFragmentDirections
                            .actionClientCatalogSelectionFragmentToInsertClientMovieRatingFragment()
                        this.findNavController().navigate(action)
                        viewModel.onCatalogItemNavigated()
                    }
                    Constants.FragmentSource.SEARCH_CLIENT_MOVIE_RATING -> {
                        sharedViewModel.setSelectedClientId(id)
                        val action = ClientCatalogSelectionFragmentDirections
                            .actionClientCatalogSelectionFragmentToSearchClientMovieRatingFragment()
                        this.findNavController().navigate(action)
                        viewModel.onCatalogItemNavigated()
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
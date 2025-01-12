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
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.activity.MainActivity
import com.example.movierentalkotlin.adapter.ClientMovieRatingCatalogItemAdapter
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentClientMovieRatingCatalogBinding
import com.example.movierentalkotlin.viewmodel.clientMovieRating.ClientMovieRatingCatalogViewModel
import com.example.movierentalkotlin.viewmodel.SharedViewModel
import com.example.movierentalkotlin.viewmodelfactory.clientMovieRating.ClientMovieRatingCatalogViewModelFactory

class ClientMovieRatingCatalogFragment : Fragment() {

    private var _binding: FragmentClientMovieRatingCatalogBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClientMovieRatingCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dao = MovieRentalDatabase.getInstance(application).clientMovieRatingDao

        val viewModelFactory = ClientMovieRatingCatalogViewModelFactory(dao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[ClientMovieRatingCatalogViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar_client_movie_rating_catalog, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.searchClientMovieRatingFragment -> {
                        val action = ClientMovieRatingCatalogFragmentDirections
                            .actionClientMovieRatingCatalogFragmentToSearchClientMovieRatingFragment()
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

        val adapter = ClientMovieRatingCatalogItemAdapter{ id ->
            viewModel.onCatalogItemClicked(id)
        }
        binding.clientMovieRatingCatalog.adapter = adapter

        sharedViewModel.clientMovieRatingFilters.observe(viewLifecycleOwner) { filters ->
            viewModel.setFilters(filters)
        }

        viewModel.catalog.observe(viewLifecycleOwner) { items ->
            items?.let {
                adapter.submitList(items)
            }
        }

        viewModel.navigateToView.observe(viewLifecycleOwner) { id ->
            id?.let {
                val action = ClientMovieRatingCatalogFragmentDirections
                    .actionClientMovieRatingCatalogFragmentToViewClientMovieRatingFragment(id)
                this.findNavController().navigate(action)
                viewModel.onCatalogItemNavigated()
            }
        }

        viewModel.navigateToInsert.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                val action = ClientMovieRatingCatalogFragmentDirections
                        .actionClientMovieRatingCatalogFragmentToInsertClientMovieRatingFragment()
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
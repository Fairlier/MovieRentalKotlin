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
import com.example.movierentalkotlin.adapter.ClientCatalogItemAdapter
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentClientCatalogBinding
import com.example.movierentalkotlin.viewmodel.ClientCatalogViewModel
import com.example.movierentalkotlin.viewmodel.SharedViewModel
import com.example.movierentalkotlin.viewmodelfactory.ClientCatalogViewModelFactory

class ClientCatalogFragment : Fragment() {

    private var _binding: FragmentClientCatalogBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClientCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dao = MovieRentalDatabase.getInstance(application).clientDao

        val viewModelFactory = ClientCatalogViewModelFactory(dao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[ClientCatalogViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar_client_catalog, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.searchClientFragment -> {
                        val action = ClientCatalogFragmentDirections
                            .actionClientCatalogFragmentToSearchClientFragment()
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
            setOf(R.id.clientCatalogFragment),
            (requireActivity() as MainActivity).binding.drawerLayout
        )
        binding.menuToolbar.setupWithNavController(navController, appBarConfiguration)

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

        viewModel.navigateToView.observe(viewLifecycleOwner) { id ->
            id?.let {
                val action = ClientCatalogFragmentDirections
                    .actionClientCatalogFragmentToViewClientFragment(id)
                this.findNavController().navigate(action)
                viewModel.onCatalogItemNavigated()
            }
        }

        viewModel.navigateToInsert.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                val action = ClientCatalogFragmentDirections
                    .actionClientCatalogFragmentToInsertClientFragment()
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
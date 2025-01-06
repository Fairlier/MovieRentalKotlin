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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.activity.MainActivity
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentSearchClientBinding
import com.example.movierentalkotlin.viewmodel.SearchClientViewModel
import com.example.movierentalkotlin.viewmodel.SharedViewModel
import com.example.movierentalkotlin.viewmodelfactory.SearchClientViewModelFactory

class SearchClientFragment : Fragment() {

    private var _binding: FragmentSearchClientBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchClientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dao = MovieRentalDatabase.getInstance(application).clientDao

        val viewModelFactory = SearchClientViewModelFactory(dao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[SearchClientViewModel::class.java]

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
            setOf(R.id.clientCatalogFragment),
            (requireActivity() as MainActivity).binding.drawerLayout
        )
        binding.menuToolbar.setupWithNavController(navController, appBarConfiguration)

        binding.menuToolbar.setNavigationOnClickListener {
            navController.navigate(SearchClientFragmentDirections
                .actionSearchClientFragmentToClientCatalogFragment())
        }

        viewModel.navigateToViewAfterSearch.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                viewModel.filters.value?.let { sharedViewModel.setClientFilters(it) }
                val action = SearchClientFragmentDirections
                    .actionSearchClientFragmentToClientCatalogFragment()
                view.findNavController().navigate(action)
                viewModel.onNavigatedToViewAfterSearch()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
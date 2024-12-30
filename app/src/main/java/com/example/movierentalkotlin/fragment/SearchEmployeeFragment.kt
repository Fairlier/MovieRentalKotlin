package com.example.movierentalkotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentSearchEmployeeBinding
import com.example.movierentalkotlin.viewmodel.SearchEmployeeViewModel
import com.example.movierentalkotlin.viewmodel.SharedViewModel
import com.example.movierentalkotlin.viewmodelfactory.SearchEmployeeViewModelFactory

class SearchEmployeeFragment : Fragment() {

    private var _binding: FragmentSearchEmployeeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchEmployeeBinding.inflate(inflater, container, false)
        val view = binding.root

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner)

        val application = requireNotNull(this.activity).application
        val dao = MovieRentalDatabase.getInstance(application).employeeDao

        val viewModelFactory = SearchEmployeeViewModelFactory(dao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[SearchEmployeeViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.navigateToCatalogAfterSearch.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                viewModel.filters.value?.let { sharedViewModel.setEmployeeFilters(it) }
                val action = SearchEmployeeFragmentDirections
                    .actionSearchEmployeeFragmentToEmployeeCatalogFragment()
                view.findNavController().navigate(action)
                viewModel.onNavigatedToCatalogAfterSearch()
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
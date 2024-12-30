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
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.adapter.EmployeeCatalogItemAdapter
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentEmployeeCatalogBinding
import com.example.movierentalkotlin.viewmodel.EmployeeCatalogViewModel
import com.example.movierentalkotlin.viewmodel.SharedViewModel
import com.example.movierentalkotlin.viewmodelfactory.EmployeeCatalogViewModelFactory

class EmployeeCatalogFragment : Fragment() {

    private var _binding: FragmentEmployeeCatalogBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmployeeCatalogBinding.inflate(inflater, container, false)
        val view = binding.root

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar_employee_catalog, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.searchEmployeeFragment -> {
                        val action = EmployeeCatalogFragmentDirections
                            .actionEmployeeCatalogFragmentToSearchEmployeeFragment()
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)

        binding.menuBottomEmployeeCatalog.setupWithNavController(findNavController())

        val application = requireNotNull(this.activity).application
        val dao = MovieRentalDatabase.getInstance(application).employeeDao

        val viewModelFactory = EmployeeCatalogViewModelFactory(dao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[EmployeeCatalogViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = EmployeeCatalogItemAdapter{ id ->
            viewModel.onCatalogItemClicked(id)
        }
        binding.employeeCatalog.adapter = adapter

        sharedViewModel.movieFilters.observe(viewLifecycleOwner) { filters ->
            viewModel.setFilters(filters)
        }

        viewModel.catalog.observe(viewLifecycleOwner, Observer { items ->
            items?.let {
                adapter.submitList(items)
            }
        })

        viewModel.navigateToView.observe(viewLifecycleOwner, Observer { id ->
            id?.let {
                val action = EmployeeCatalogFragmentDirections
                    .actionEmployeeCatalogFragmentToViewEmployeeFragment(id)
                this.findNavController().navigate(action)
                viewModel.onCatalogItemNavigated()
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
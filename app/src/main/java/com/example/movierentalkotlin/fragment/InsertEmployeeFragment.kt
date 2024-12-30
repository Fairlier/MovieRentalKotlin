package com.example.movierentalkotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentInsertEmployeeBinding
import com.example.movierentalkotlin.util.Constants
import com.example.movierentalkotlin.viewmodel.InsertEmployeeViewModel
import com.example.movierentalkotlin.viewmodelfactory.InsertEmployeeViewModelFactory

class InsertEmployeeFragment : Fragment() {

    private var _binding: FragmentInsertEmployeeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInsertEmployeeBinding.inflate(inflater, container, false)
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

        val viewModelFactory = InsertEmployeeViewModelFactory(dao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[InsertEmployeeViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.currentImageUrl.observe(viewLifecycleOwner) { url ->
            Glide.with(this)
                .load(url)
                .placeholder(R.drawable.baseline_image_not_supported_24)
                .error(R.drawable.baseline_image_not_supported_24)
                .into(binding.employeeImage)
        }

        viewModel.navigateToCatalogAfterInsert.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                val action = InsertEmployeeFragmentDirections
                    .actionInsertEmployeeFragmentToEmployeeCatalogFragment()
                view.findNavController().navigate(action)
                viewModel.onNavigatedToCatalogAfterInsert()
            }
        })

        viewModel.showValidationError.observe(viewLifecycleOwner) { shouldShow ->
            if (shouldShow == true) {
                Toast.makeText(
                    requireContext(),
                    Constants.INSERT_TEXT,
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.onValidationErrorShown()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
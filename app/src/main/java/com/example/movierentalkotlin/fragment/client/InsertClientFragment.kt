package com.example.movierentalkotlin.fragment.client

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.activity.MainActivity
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentInsertClientBinding
import com.example.movierentalkotlin.util.Constants
import com.example.movierentalkotlin.viewmodel.client.InsertClientViewModel
import com.example.movierentalkotlin.viewmodelfactory.client.InsertClientViewModelFactory
import java.util.Calendar

class InsertClientFragment : Fragment() {

    private var _binding: FragmentInsertClientBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInsertClientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dao = MovieRentalDatabase.getInstance(application).clientDao

        val viewModelFactory = InsertClientViewModelFactory(dao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[InsertClientViewModel::class.java]

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
            navController.navigate(InsertClientFragmentDirections.actionInsertClientFragmentToClientCatalogFragment())
        }

        viewModel.currentImageUrl.observe(viewLifecycleOwner) { url ->
            Glide.with(this)
                .load(url)
                .placeholder(R.drawable.baseline_image_not_supported_24)
                .error(R.drawable.baseline_image_not_supported_24)
                .into(binding.clientImage)
        }

        viewModel.navigateToCatalogAfterInsert.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                val action =
                    InsertClientFragmentDirections.actionInsertClientFragmentToClientCatalogFragment()
                view.findNavController().navigate(action)
                viewModel.onNavigatedToCatalogAfterInsert()
            }
        }

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

        viewModel.showDatePickerForField.observe(viewLifecycleOwner) { field ->
            if (field != null) {
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
                val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { _, year, month, dayOfMonth ->
                        viewModel.onDateSelected(year, month, dayOfMonth, field)
                    },
                    currentYear,
                    currentMonth,
                    currentDay
                )
                datePickerDialog.show()

                viewModel.onDatePickerShown()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
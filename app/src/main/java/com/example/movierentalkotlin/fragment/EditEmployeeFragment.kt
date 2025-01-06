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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.activity.MainActivity
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentEditEmployeeBinding
import com.example.movierentalkotlin.viewmodel.EditEmployeeViewModel
import com.example.movierentalkotlin.viewmodelfactory.EditEmployeeViewModelFactory

class EditEmployeeFragment : Fragment() {

    private var _binding: FragmentEditEmployeeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditEmployeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dao = MovieRentalDatabase.getInstance(application).employeeDao

        val id = EditEmployeeFragmentArgs.fromBundle(requireArguments()).id
        val viewModelFactory = EditEmployeeViewModelFactory(id, dao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[EditEmployeeViewModel::class.java]

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
            setOf(R.id.employeeCatalogFragment),
            (requireActivity() as MainActivity).binding.drawerLayout
        )
        binding.menuToolbar.setupWithNavController(navController, appBarConfiguration)

        binding.menuToolbar.setNavigationOnClickListener {
            navController.navigate(EditEmployeeFragmentDirections
                .actionEditEmployeeFragmentToViewEmployeeFragment(id))
        }

        viewModel.currentImageUrl.observe(viewLifecycleOwner) { url ->
            Glide.with(this)
                .load(url)
                .placeholder(R.drawable.baseline_image_not_supported_24)
                .error(R.drawable.baseline_image_not_supported_24)
                .into(binding.employeeImage)
        }

        viewModel.navigateToViewAfterUpdate.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                val action = EditEmployeeFragmentDirections
                    .actionEditEmployeeFragmentToViewEmployeeFragment(id)
                view.findNavController().navigate(action)
                viewModel.onNavigatedToViewAfterUpdate()
            }
        }

        viewModel.navigateToViewAfterDelete.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                val action = EditEmployeeFragmentDirections
                    .actionEditEmployeeFragmentToEmployeeCatalogFragment()
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
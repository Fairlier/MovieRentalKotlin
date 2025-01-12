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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.activity.MainActivity
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentViewClientMovieRatingBinding
import com.example.movierentalkotlin.viewmodel.clientMovieRating.ViewClientMovieRatingViewModel
import com.example.movierentalkotlin.viewmodelfactory.clientMovieRating.ViewClientMovieRatingViewModelFactory

class ViewClientMovieRatingFragment : Fragment() {

    private var _binding: FragmentViewClientMovieRatingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewClientMovieRatingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val clientMovieRatingDao = MovieRentalDatabase.getInstance(application).clientMovieRatingDao
        val clientDao = MovieRentalDatabase.getInstance(application).clientDao
        val movieDao = MovieRentalDatabase.getInstance(application).movieDao

        val id = ViewClientMovieRatingFragmentArgs.fromBundle(requireArguments()).id
        val viewModelFactory = ViewClientMovieRatingViewModelFactory(id, clientMovieRatingDao, clientDao, movieDao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[ViewClientMovieRatingViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar_view_employee, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.editClientMovieRatingFragment -> {
                        val currentId = ViewClientMovieRatingFragmentArgs.fromBundle(requireArguments()).id
                        val action =
                            ViewClientMovieRatingFragmentDirections
                                .actionViewClientMovieRatingFragmentToEditClientMovieRatingFragment(currentId)
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
            navController.navigate(ViewClientMovieRatingFragmentDirections
                .actionViewClientMovieRatingFragmentToClientMovieRatingCatalogFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
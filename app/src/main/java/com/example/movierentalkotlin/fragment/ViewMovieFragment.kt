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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentViewMovieBinding
import com.example.movierentalkotlin.viewmodel.ViewMovieViewModel
import com.example.movierentalkotlin.viewmodelfactory.ViewMovieViewModelFactory

class ViewMovieFragment : Fragment() {

    private var _binding: FragmentViewMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentViewMovieBinding.inflate(inflater, container, false)
        val view = binding.root

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar_view_movie, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.editMovieFragment -> {
                        val id = ViewMovieFragmentArgs.fromBundle(requireArguments()).id
                        val action = ViewMovieFragmentDirections
                            .actionViewMovieFragmentToEditMovieFragment(id)
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)

        val application = requireNotNull(this.activity).application
        val dao = MovieRentalDatabase.getInstance(application).movieDao

        val id = ViewMovieFragmentArgs.fromBundle(requireArguments()).id
        val viewModelFactory = ViewMovieViewModelFactory(id, dao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[ViewMovieViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
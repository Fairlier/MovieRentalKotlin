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
import com.example.movierentalkotlin.databinding.FragmentViewClientBinding
import com.example.movierentalkotlin.viewmodel.ViewClientViewModel
import com.example.movierentalkotlin.viewmodelfactory.ViewClientViewModelFactory

class ViewClientFragment : Fragment() {

    private var _binding: FragmentViewClientBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentViewClientBinding.inflate(inflater, container, false)
        val view = binding.root

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar_view_client, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.editClientFragment -> {
                        val id = ViewClientFragmentArgs.fromBundle(requireArguments()).id
                        val action = ViewClientFragmentDirections.actionViewClientFragmentToEditClientFragment(id)
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)

        val application = requireNotNull(this.activity).application
        val dao = MovieRentalDatabase.getInstance(application).clientDao

        val id = ViewMovieFragmentArgs.fromBundle(requireArguments()).id
        val viewModelFactory = ViewClientViewModelFactory(id, dao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory)[ViewClientViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
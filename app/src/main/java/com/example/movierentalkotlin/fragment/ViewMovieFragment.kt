package com.example.movierentalkotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
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
        val taskId = ViewMovieFragmentArgs.fromBundle(requireArguments()).id

        val application = requireNotNull(this.activity).application
        val dao = MovieRentalDatabase.getInstance(application).movieDao

        val viewModelFactory = ViewMovieViewModelFactory(taskId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ViewMovieViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.navigateToCatalog.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                view.findNavController()
                    .navigate(R.id.action_viewMovieFragment_to_movieCatalogFragment)
                viewModel.onNavigatedToCatalog()
            }
        })
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
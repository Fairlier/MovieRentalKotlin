package com.example.movierentalkotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movierentalkotlin.adapter.MovieCatalogItemAdapter
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.databinding.FragmentMovieCatalogBinding
import com.example.movierentalkotlin.viewmodel.MovieCatalogViewModel
import com.example.movierentalkotlin.viewmodelfactory.MovieCatalogViewModelFactory

class MovieCatalogFragment : Fragment() {

    private var _binding: FragmentMovieCatalogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieCatalogBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = MovieRentalDatabase.getInstance(application).movieDao

        val viewModelFactory = MovieCatalogViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
                        .get(MovieCatalogViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = MovieCatalogItemAdapter{ id ->
            viewModel.onMovieClicked(id)
        }
        binding.movieCatalog.adapter = adapter

        viewModel.movieCatalog.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToMovie.observe(viewLifecycleOwner, Observer { id ->
            id?.let {
                val action = MovieCatalogFragmentDirections
                    .actionMovieCatalogFragmentToViewMovieFragment(id)
                this.findNavController().navigate(action)
                viewModel.onMovieNavigated()
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
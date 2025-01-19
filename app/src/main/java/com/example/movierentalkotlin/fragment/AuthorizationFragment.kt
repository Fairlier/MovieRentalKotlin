package com.example.movierentalkotlin.fragment

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.movierentalkotlin.databinding.FragmentAuthorizationBinding
import com.example.movierentalkotlin.util.Constants
import com.example.movierentalkotlin.util.SessionManager
import com.example.movierentalkotlin.viewmodel.AuthorizationViewModel

class AuthorizationFragment : Fragment() {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val sessionManager = SessionManager(this.requireContext())

        val roles = arrayOf(Constants.Role.ADMINISTRATOR)
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            roles
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerRole.adapter = adapter

        binding.spinnerRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedRole = roles[position]
                viewModel.selectRole(selectedRole)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.selectRole("")
            }
        }

        viewModel.navigateToCatalog.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                sessionManager.setLoginState(true, viewModel.selectedRole.value.toString())
                val action = AuthorizationFragmentDirections
                    .actionAuthorizationFragmentToMovieCatalogFragment()
                view.findNavController().navigate(action)
                viewModel.onNavigatedToCatalog()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
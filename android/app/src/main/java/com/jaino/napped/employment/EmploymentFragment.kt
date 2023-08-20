package com.jaino.napped.employment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaino.napped.R
import com.jaino.napped.databinding.FragmentEmploymentBinding
import com.jaino.napped.employment.adapter.CompanyAdapter
import com.jaino.napped.employment.adapter.EmploymentAdapter
import com.jaino.napped.model.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class EmploymentFragment: Fragment() {

    private var _binding: FragmentEmploymentBinding? = null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized" }

    private val viewModel: EmploymentViewModel by viewModels()
    private lateinit var companyAdapter: CompanyAdapter
    private lateinit var employmentAdapter: EmploymentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initButton()
        initEmploymentAdapter()
        viewModel.getEmploymentList()
    }

    private fun initButton(){
        binding.employmentChip.setOnClickListener{
            initEmploymentAdapter()
            viewModel.getEmploymentList()
        }

        binding.companyChip.setOnClickListener {
            initCompanyAdapter()
            viewModel.getCompanyList()
        }
    }

    private fun initCompanyAdapter(){
        companyAdapter = CompanyAdapter()
        binding.employmentList.adapter = companyAdapter
        binding.employmentList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initEmploymentAdapter(){
        employmentAdapter = EmploymentAdapter(
            onClick = {
                navigateToInformation()
            }
        )
        binding.employmentList.adapter = employmentAdapter
        binding.employmentList.layoutManager = LinearLayoutManager(requireContext())
    }
    private fun observeData(){
        viewModel.companyItemUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is UiState.Success -> {
                        if(it.data.isNotEmpty()) {
                            companyAdapter.submitList(it.data)
                        }
                    }

                    is UiState.Init -> {}

                    is UiState.Failure -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.employmentItemUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is UiState.Success -> {
                        if(it.data.isNotEmpty()) {
                            employmentAdapter.submitList(it.data)
                        }
                    }

                    is UiState.Init -> {}

                    is UiState.Failure -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToInformation(){
        val direction = EmploymentFragmentDirections.actionEmploymentFragmentToInformationFragment()
        findNavController().navigate(direction)
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
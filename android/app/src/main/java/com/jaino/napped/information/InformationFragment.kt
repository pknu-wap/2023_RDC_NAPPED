package com.jaino.napped.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jaino.napped.R
import com.jaino.napped.databinding.FragmentInformationBinding
import com.jaino.napped.model.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class InformationFragment: Fragment() {
    private var _binding: FragmentInformationBinding? = null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized" }

    private val viewModel: InformationViewModel by viewModels()
    private val args: InformationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_information, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
        initData()
        observeData()
    }

    private fun initButton(){
        binding.employmentBackButton.setOnClickListener{
            val direction = InformationFragmentDirections.actionInformationFragmentToEmploymentFragment()
            findNavController().navigate(direction)
        }

        binding.btInformationScrap.setOnClickListener{
            viewModel.addFavorite()
        }

        binding.btInformationApply.setOnClickListener {
            Toast.makeText(requireContext(), "지원에 성공하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initData(){
        viewModel.getEmploymentInformation(args.employmentId)
    }

    private fun observeData(){
        viewModel.informationUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is UiEvent.Success -> {
                        Toast.makeText(requireContext(), "관심 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                    }

                    is UiEvent.Failure -> {
                        Timber.d(it.error)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
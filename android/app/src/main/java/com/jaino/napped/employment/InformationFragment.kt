package com.jaino.napped.employment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jaino.napped.R
import com.jaino.napped.databinding.FragmentInformationBinding

class InformationFragment: Fragment() {
    private var _binding: FragmentInformationBinding? = null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_information, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
    }

    private fun initButton(){
        binding.employmentBackButton.setOnClickListener{
            val direction = InformationFragmentDirections.actionInformationFragmentToEmploymentFragment()
            findNavController().navigate(direction)
        }

        binding.btInformationScrap.setOnClickListener{
            Toast.makeText(requireContext(), "스크랩 되었습니다.", Toast.LENGTH_SHORT).show()
        }

        binding.btInformationApply.setOnClickListener {
            Toast.makeText(requireContext(), "지원에 성공하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
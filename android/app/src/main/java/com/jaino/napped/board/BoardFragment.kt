package com.jaino.napped.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaino.napped.R
import com.jaino.napped.board.adapter.BoardAdapter
import com.jaino.napped.databinding.FragmentBoardBinding
import com.jaino.napped.model.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BoardFragment: Fragment() {

    private var _binding: FragmentBoardBinding? = null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized" }

    private val viewModel: BoardViewModel by viewModels()
    private lateinit var adapter: BoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModelState()
        initAdapter()
        observeData()
    }

    private fun initViewModelState(){
        viewModel.getBoardList()
    }

    private fun initAdapter(){
        adapter = BoardAdapter()
        binding.rvBoardList.adapter = adapter
        binding.rvBoardList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeData(){
        viewModel.boardUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is UiState.Success -> {
                        if(it.data.isNotEmpty()) {
                            adapter.submitList(it.data)
                        }
                    }

                    is UiState.Init -> {}

                    is UiState.Failure -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
package com.jaino.napped.favorite

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
import com.jaino.domain.model.Favorite
import com.jaino.napped.R
import com.jaino.napped.databinding.FragmentFavoritesBinding
import com.jaino.napped.favorite.adapter.FavoriteAdapter
import com.jaino.napped.model.UiEvent
import com.jaino.napped.model.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class FavoritesFragment: Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized" }

    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initViewModelState()
        observeData()
    }

    private fun initAdapter(){
        adapter = FavoriteAdapter(
            onRemoveButtonClicked = { favorite ->
                removeItem(favorite)
            }
        )
        binding.rvFavorite.adapter = adapter
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initViewModelState(){
        viewModel.getFavoriteList()
    }

    private fun observeData(){
        viewModel.favoriteListUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
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

        viewModel.favoriteUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is UiEvent.Success -> {
                    }

                    is UiEvent.Failure -> {
                        Timber.e(it.error)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun removeItem(favorite: Favorite){
        viewModel.removeFavorite(favorite)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
package com.jaino.napped.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaino.domain.model.Favorite
import com.jaino.domain.repository.FavoriteRepository
import com.jaino.napped.model.UiEvent
import com.jaino.napped.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavoriteRepository
): ViewModel() {

    private val _favoriteListUiState = MutableStateFlow<UiState<List<Favorite>>>(UiState.Init)
    val favoriteListUiState: StateFlow<UiState<List<Favorite>>>
        get() =_favoriteListUiState

    private val _favoriteUiEvent = MutableSharedFlow<UiEvent<Unit>>()
    val favoriteUiEvent: SharedFlow<UiEvent<Unit>> get() = _favoriteUiEvent

    fun getFavoriteList(){
        viewModelScope.launch {
            repository.getFavoriteList()
                .onSuccess { favoriteListFlow ->
                    favoriteListFlow.collectLatest { list ->
                        _favoriteListUiState.value = UiState.Success(list)
                    }
                }
                .onFailure { error ->
                    _favoriteUiEvent.emit(
                        UiEvent.Failure(error)
                    )
                }
        }
    }

    fun removeFavorite(favorite: Favorite){
        viewModelScope.launch {
            repository.deleteFavorites(favorite)
                .onSuccess {
                }
                .onFailure { error ->
                    _favoriteUiEvent.emit(
                        UiEvent.Failure(error)
                    )
                }
        }
    }
}
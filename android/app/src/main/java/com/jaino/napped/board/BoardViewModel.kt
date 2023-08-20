package com.jaino.napped.board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaino.domain.model.Board
import com.jaino.domain.repository.BoardRepository
import com.jaino.napped.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val boardRepository: BoardRepository
): ViewModel(){

    private val _boardUiState = MutableStateFlow<UiState<List<Board>>>(UiState.Init)
    val boardUiState: StateFlow<UiState<List<Board>>> get() = _boardUiState

    fun getBoardList(){
        viewModelScope.launch {
            boardRepository.getBoardList()
                .onSuccess {  list ->
                    _boardUiState.value = UiState.Success(list)
                }
                .onFailure {

                }
        }
    }
}
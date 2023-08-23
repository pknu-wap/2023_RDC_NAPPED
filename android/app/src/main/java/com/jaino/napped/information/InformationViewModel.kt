package com.jaino.napped.information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaino.domain.model.Employment
import com.jaino.domain.model.Favorite
import com.jaino.domain.repository.EmploymentRepository
import com.jaino.domain.repository.FavoriteRepository
import com.jaino.napped.model.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(
    private val employmentRepository: EmploymentRepository,
    private val favoriteRepository: FavoriteRepository
): ViewModel() {

    private val _employmentInformation = MutableStateFlow<Employment>(Employment())
    val employmentInformation: StateFlow<Employment> get() = _employmentInformation

    private val _informationUiEvent = MutableSharedFlow<UiEvent<Unit>>()
    val informationUiEvent: SharedFlow<UiEvent<Unit>>
        get() = _informationUiEvent

    fun getEmploymentInformation(id: Long){
        viewModelScope.launch {
            employmentRepository.getEmploymentById(id)
                .onSuccess {
                    _employmentInformation.value = it
                }
                .onFailure {

                }
        }
    }

    fun addFavorite(){
        viewModelScope.launch {
            val favorite = getFavorite()
            favoriteRepository.insertFavorite(favorite)
                .onSuccess {
                    _informationUiEvent.emit(
                        UiEvent.Success(Unit)
                    )
                }
                .onFailure { error ->
                    _informationUiEvent.emit(
                        UiEvent.Failure(error)
                    )
                }
        }
    }

    private fun getFavorite(): Favorite{
         return with(_employmentInformation.value){
            Favorite(
                company = this.company,
                kind = this.kind,
                location = location
            )
        }
    }
}
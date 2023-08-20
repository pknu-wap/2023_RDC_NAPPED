package com.jaino.napped.employment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaino.domain.model.Company
import com.jaino.domain.model.Employment
import com.jaino.domain.repository.CompanyRepository
import com.jaino.domain.repository.EmploymentRepository
import com.jaino.napped.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EmploymentViewModel @Inject constructor(
    private val employmentRepository: EmploymentRepository,
    private val companyRepository: CompanyRepository
): ViewModel(){

    private val _companyItemUiState = MutableSharedFlow<UiState<List<Company>>>()
    val companyItemUiState: SharedFlow<UiState<List<Company>>> get() = _companyItemUiState

    private val _employmentItemUiState = MutableSharedFlow<UiState<List<Employment>>>()
    val employmentItemUiState: SharedFlow<UiState<List<Employment>>> get() = _employmentItemUiState

    fun getEmploymentList(){
        viewModelScope.launch {
            employmentRepository.getEmploymentList(pageCount = 10, page = 2) // TODO PAGING
                .onSuccess { list ->
                    _employmentItemUiState.emit(UiState.Success(list))
                }
                .onFailure {

                }
        }
    }

    fun getCompanyList(){
        viewModelScope.launch {
            companyRepository.getCompanyList()
                .onSuccess { list ->
                    _companyItemUiState.emit(UiState.Success(list))
                }
                .onFailure {

                }
        }
    }
}

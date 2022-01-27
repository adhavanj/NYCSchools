package com.codingchallenge.nycschools.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingchallenge.nycschools.data.SchoolRepository
import com.codingchallenge.nycschools.data.local.SchoolEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Collections.emptyList
import javax.inject.Inject

sealed class SchoolListUiState {
    data class Success(val schoolItems: List<SchoolItemUiState>) : SchoolListUiState()
    data class Error(val exception: Throwable) : SchoolListUiState()
    object Loading : SchoolListUiState()
    object Empty : SchoolListUiState()

    companion object {
        fun toUiState(schoolEntityList: List<SchoolEntity>): List<SchoolItemUiState> {
            return schoolEntityList.fold(emptyList()) { acc, schoolEntity ->
                acc.plus(
                    SchoolItemUiState(
                        dbn = schoolEntity.dbn,
                        name = schoolEntity.schoolName ?: "",
                        phoneNumber = schoolEntity.phoneNumber ?: "",
                        email = schoolEntity.schoolEmail ?: "",
                        website = schoolEntity.website ?: "",
                        primaryAddressLine1 = schoolEntity.primaryAddressLine1 ?: "",
                        zip = schoolEntity.zip ?: "",
                        city = schoolEntity.city ?: ""
                    )
                )
            }
        }
    }
}

data class SchoolItemUiState(
    val dbn: String,
    val name: String,
    val phoneNumber: String,
    val email: String,
    val website: String,
    val primaryAddressLine1: String,
    val zip: String,
    val city: String
)

@HiltViewModel
class SchoolListViewModel @Inject constructor(
    private val repository: SchoolRepository,
    private val isNetworkAvailable: Boolean
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<SchoolListUiState>(SchoolListUiState.Success(emptyList()))

    val uiState: StateFlow<SchoolListUiState>
        get() = _uiState.stateIn(viewModelScope, SharingStarted.Lazily, SchoolListUiState.Loading)

    init {
        viewModelScope.launch {
            try {
                _uiState.value = SchoolListUiState.Loading
                repository.fetchSchoolList()
                if (isNetworkAvailable) {
                    repository.getSchoolList()
                        .collect { schoolEntities ->
                            if (schoolEntities.isEmpty()) {
                                _uiState.value =
                                    SchoolListUiState.Empty
                            } else {
                                _uiState.value =
                                    SchoolListUiState.Success(
                                        SchoolListUiState.toUiState(
                                            schoolEntities
                                        )
                                    )
                            }
                        }
                } else {
                    _uiState.value =
                        SchoolListUiState.Error(Exception("No Network. Please connect to a working internet"))
                }
            } catch (exception: IOException) {
                _uiState.value = SchoolListUiState.Error(exception)
            }
        }
    }
}
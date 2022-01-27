package com.codingchallenge.nycschools.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingchallenge.nycschools.data.SchoolRepository
import com.codingchallenge.nycschools.data.local.SchoolEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


sealed class SchoolDetailScreenUiState {
    data class Success(val schoolDetails: SchoolDetailUiState) : SchoolDetailScreenUiState()
    data class Error(val exception: Throwable) : SchoolDetailScreenUiState()
    object Loading : SchoolDetailScreenUiState()

    companion object {
        fun toUiState(schoolEntity: SchoolEntity): SchoolDetailUiState {
            return SchoolDetailUiState(
                dbn = schoolEntity.dbn,
                name = schoolEntity.schoolName,
                overviewParagraph = schoolEntity.overviewParagraph,
                numOfSatTestTakers = schoolEntity.numOfSatTestTakers,
                satCriticalReadingAvgScore = schoolEntity.satCriticalReadingAvgScore,
                satMathAvgScore = schoolEntity.satMathAvgScore,
                satWritingAvgScore = schoolEntity.satWritingAvgScore
            )
        }
    }
}

data class SchoolDetailUiState(
    val dbn: String,
    val name: String,
    val overviewParagraph: String,
    val numOfSatTestTakers: String,
    val satCriticalReadingAvgScore: String,
    val satMathAvgScore: String,
    val satWritingAvgScore: String
)

@HiltViewModel
class SchoolDetailViewModel @Inject constructor(
    private val repository: SchoolRepository,
    private val isNetworkAvailable: Boolean
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<SchoolDetailScreenUiState>(SchoolDetailScreenUiState.Loading)


    val uiState: StateFlow<SchoolDetailScreenUiState>
        get() = _uiState.stateIn(viewModelScope, SharingStarted.Lazily, SchoolDetailScreenUiState.Loading)

    fun getSchoolDetails(dbn: String) {
        viewModelScope.launch {
            try {
                _uiState.value = SchoolDetailScreenUiState.Loading
                repository.fetchSchoolSatDetails(dbn)
                if (isNetworkAvailable) {
                    repository.getSchoolSatDetails(dbn)
                        .collect { schoolEntities ->
                            _uiState.value =
                                SchoolDetailScreenUiState.Success(
                                    SchoolDetailScreenUiState.toUiState(
                                        schoolEntities
                                    )
                                )
                        }
                } else {
                    _uiState.value =
                        SchoolDetailScreenUiState.Error(Exception("No Network. Please connect to a working internet"))
                }
            } catch (exception: IOException) {
                _uiState.value = SchoolDetailScreenUiState.Error(exception)
            }
        }
    }
}
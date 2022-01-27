package com.codingchallenge.nycschools.data

import android.util.Log
import com.codingchallenge.nycschools.data.local.SchoolDao
import com.codingchallenge.nycschools.data.local.SchoolEntity
import com.codingchallenge.nycschools.data.local.SchoolSatDetailsEntity
import com.codingchallenge.nycschools.data.remote.Results
import com.codingchallenge.nycschools.data.remote.SchoolRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SchoolRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val schoolRemoteDataSource: SchoolRemoteDataSource,
    private val schoolDao: SchoolDao
) {

    suspend fun getSchoolList(): Flow<List<SchoolEntity>> {
        return withContext(dispatcher) {
            schoolDao.getAllSchool().distinctUntilChanged()
        }
    }

    suspend fun fetchSchoolList() {
        return withContext(dispatcher) {
            when (val schoolList = schoolRemoteDataSource.fetchSchoolList()) {
                is Results.Error -> {
                    Log.e(TAG, "${schoolList.throwable.message}")
                }
                is Results.Success -> {
                    schoolDao.nukeTable()
                    schoolDao.insertAll(SchoolEntity.toEntity(schoolList.data))
                }
            }
        }
    }

    suspend fun getSchoolSatDetails(dbn: String): Flow<SchoolEntity> {
        return withContext(dispatcher) {
            schoolDao.getSchoolEntity(dbn).distinctUntilChanged()
        }
    }

    suspend fun fetchSchoolSatDetails(dbn: String) {
        return withContext(dispatcher) {
            when (val schoolList = schoolRemoteDataSource.fetchSchoolSatDetails(dbn)) {
                is Results.Error -> {
                    Log.e(TAG, "${schoolList.throwable.message}")
                }
                is Results.Success -> {
                    if (schoolList.data.isEmpty().not()) {
                        schoolDao.update(SchoolSatDetailsEntity.toEntity(schoolList.data))
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "SchoolListRepository"
    }
}
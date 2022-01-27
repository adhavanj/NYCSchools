package com.codingchallenge.nycschools.data.remote

import javax.inject.Inject

class SchoolRemoteDataSource @Inject constructor(private val schoolApiService: SchoolApiService) {

    // These functions can be write as higher-order to avoid duplicate code
    suspend fun fetchSchoolList(): Results<List<SchoolListModel>> {
        return try {
            val result = schoolApiService.getSchoolList()
            if (result.isSuccessful && result.body() != null) {
                Results.Success(result.body()!!)
            } else {
                Results.Error(Exception("Error"))
            }
        } catch (e: Throwable) {
            Results.Error(e)
        }
    }

    suspend fun fetchSchoolSatDetails(dbn: String): Results<List<SchoolSatDetailModelItem>> {
        return try {
            val result = schoolApiService.getSchoolSatDetails(dbn)
            if (result.isSuccessful && result.body() != null) {
                Results.Success(result.body()!!)
            } else {
                Results.Error(Exception("Error"))
            }
        } catch (e: Throwable) {
            Results.Error(e)
        }
    }
}
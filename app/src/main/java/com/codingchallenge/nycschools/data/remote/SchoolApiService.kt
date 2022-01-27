package com.codingchallenge.nycschools.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolApiService {
    @GET("/resource/s3k6-pzi2.json")
    suspend fun getSchoolList(): Response<List<SchoolListModel>>

    @GET("/resource/f9bf-2cp4.json")
    suspend fun getSchoolSatDetails(@Query("dbn") dbn: String): Response<List<SchoolSatDetailModelItem>>
}
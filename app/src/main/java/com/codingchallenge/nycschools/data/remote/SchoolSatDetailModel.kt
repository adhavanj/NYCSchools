package com.codingchallenge.nycschools.data.remote

import com.squareup.moshi.Json

data class SchoolSatDetailModelItem(
    @Json(name = "dbn")
    val dbn: String,
    @field:Json(name = "num_of_sat_test_takers")
    val numOfSatTestTakers: String?,
    @field:Json(name = "sat_critical_reading_avg_score")
    val satCriticalReadingAvgScore: String?,
    @field:Json(name = "sat_math_avg_score")
    val satMathAvgScore: String?,
    @field:Json(name = "sat_writing_avg_score")
    val satWritingAvgScore: String?,
    @field:Json(name = "school_name")
    val schoolName: String?
)

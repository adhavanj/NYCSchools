package com.codingchallenge.nycschools.data.remote

import com.squareup.moshi.Json

data class SchoolListModel(
    @Json(name = "dbn")
    val dbn: String?,
    @field:Json(name = "overview_paragraph")
    val overviewParagraph: String?,
    @field:Json(name = "phone_number")
    val phoneNumber: String?,
    @field:Json(name = "school_email")
    val schoolEmail: String?,
    @field:Json(name = "school_name")
    val schoolName: String?,
    @field:Json(name = "primary_address_line_1")
    val primaryAddressLine1: String?,
    @Json(name = "website")
    val website: String?,
    @Json(name = "city")
    val city: String?,
    @Json(name = "zip")
    val zip: String?

)

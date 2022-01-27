package com.codingchallenge.nycschools.data.local

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingchallenge.nycschools.data.local.SchoolEntity.Companion.TABLE_NAME_SCHOOL
import com.codingchallenge.nycschools.data.remote.SchoolListModel
import com.codingchallenge.nycschools.data.remote.SchoolSatDetailModelItem

@Entity(tableName = TABLE_NAME_SCHOOL)
data class SchoolEntity(
    @NonNull
    @PrimaryKey(autoGenerate = false)
    val dbn: String,
    val primaryAddressLine1: String,
    val overviewParagraph: String,
    val phoneNumber: String,
    val schoolEmail: String,
    val schoolName: String,
    val website: String,
    val zip: String,
    val city: String,
    val numOfSatTestTakers: String,
    val satCriticalReadingAvgScore: String,
    val satMathAvgScore: String,
    val satWritingAvgScore: String
) {
    companion object {
        const val TABLE_NAME_SCHOOL = "School"

        fun toEntity(responseList: List<SchoolListModel>): List<SchoolEntity> {
            return responseList.fold(emptyList()) { acc, item ->
                val schoolEntity = if (item.dbn != null) SchoolEntity(
                    dbn = item.dbn,
                    primaryAddressLine1 = item.primaryAddressLine1 ?: "",
                    overviewParagraph = item.overviewParagraph ?: "",
                    phoneNumber = item.phoneNumber ?: "",
                    schoolEmail = item.schoolEmail ?: "",
                    schoolName = item.schoolName ?: "",
                    website = item.website ?: "",
                    zip = item.zip ?: "",
                    city = item.city ?: "",
                    numOfSatTestTakers = "",
                    satCriticalReadingAvgScore = "",
                    satMathAvgScore = "",
                    satWritingAvgScore = "",
                ) else null
                acc.plus(schoolEntity).filterNotNull()
            }
        }
    }
}

// Update only part of the school table
data class SchoolSatDetailsEntity(
    val dbn: String,
    val numOfSatTestTakers: String,
    val satCriticalReadingAvgScore: String,
    val satMathAvgScore: String,
    val satWritingAvgScore: String
) {
    companion object {
        fun toEntity(items: List<SchoolSatDetailModelItem>): SchoolSatDetailsEntity {
            val item = items.first()
            return SchoolSatDetailsEntity(
                dbn = item.dbn,
                numOfSatTestTakers = item.numOfSatTestTakers ?: "",
                satCriticalReadingAvgScore = item.satCriticalReadingAvgScore ?: "",
                satMathAvgScore = item.satMathAvgScore ?: "",
                satWritingAvgScore = item.satWritingAvgScore ?: ""
            )
        }
    }
}

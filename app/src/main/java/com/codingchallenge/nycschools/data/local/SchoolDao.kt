package com.codingchallenge.nycschools.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SchoolDao {

    @Query("SELECT * FROM ${SchoolEntity.TABLE_NAME_SCHOOL}")
    fun getAllSchool(): Flow<List<SchoolEntity>>

    @Query("SELECT * FROM ${SchoolEntity.TABLE_NAME_SCHOOL} where dbn = :dbn")
    fun getSchoolEntity(dbn: String): Flow<SchoolEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(school: List<SchoolEntity>)

    @Update(entity = SchoolEntity::class)
    fun update(obj: SchoolSatDetailsEntity)

    @Query("DELETE FROM ${SchoolEntity.TABLE_NAME_SCHOOL}")
    fun nukeTable()
}
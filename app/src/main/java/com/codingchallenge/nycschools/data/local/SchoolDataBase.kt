package com.codingchallenge.nycschools.data.local


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SchoolEntity::class], version = 1, exportSchema = true)
abstract class SchoolDataBase : RoomDatabase() {
    abstract fun schoolDao(): SchoolDao
}

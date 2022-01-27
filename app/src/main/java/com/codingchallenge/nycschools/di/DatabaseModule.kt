package com.codingchallenge.nycschools.di

import android.content.Context
import androidx.room.Room
import com.codingchallenge.nycschools.data.local.SchoolDao
import com.codingchallenge.nycschools.data.local.SchoolDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSchoolDatabase(@ApplicationContext appContext: Context): SchoolDataBase {
        return Room.databaseBuilder(
            appContext,
            SchoolDataBase::class.java,
            "school.db"
        ).build()
    }

    @Provides
    fun provideSchoolDao(schoolDataBase: SchoolDataBase): SchoolDao {
        return schoolDataBase.schoolDao()
    }
}
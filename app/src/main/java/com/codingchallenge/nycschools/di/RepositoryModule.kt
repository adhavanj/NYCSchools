package com.codingchallenge.nycschools.di

import com.codingchallenge.nycschools.data.local.SchoolDao
import com.codingchallenge.nycschools.data.remote.SchoolRemoteDataSource
import com.codingchallenge.nycschools.data.SchoolRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideSchoolListRepository(
        schoolRemoteDataSource: SchoolRemoteDataSource,
        schoolDao: SchoolDao
    ): SchoolRepository {
        return SchoolRepository(
            schoolRemoteDataSource = schoolRemoteDataSource,
            schoolDao = schoolDao
        )
    }
}
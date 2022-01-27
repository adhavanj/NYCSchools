package com.codingchallenge.nycschools.di

import android.content.Context
import android.net.ConnectivityManager
import com.codingchallenge.nycschools.data.remote.SchoolApiService
import com.codingchallenge.nycschools.data.remote.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()

    }

    @Provides
    fun provideSchoolApiService(retrofit: Retrofit): SchoolApiService {
        return retrofit.create(SchoolApiService::class.java)
    }

    // Using deprecated method for this sample app
    @Provides
    fun providesNetworkAvailability(@ApplicationContext appContext: Context): Boolean {
        val connMgr =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
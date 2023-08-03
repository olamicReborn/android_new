package com.maggnet.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maggnet.BuildConfig
import com.maggnet.di.qualifier.NetworkInfo
import com.maggnet.domain.SessionManager
import com.maggnet.domain.remote.GsonProvider
import com.maggnet.utils.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setDateFormat(GsonProvider.ISO_8601_DATE_FORMAT).create()
    }

    @Provides
    @NetworkInfo
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext appContext: Context) =
        PreferenceManager(appContext)


    @Provides
    @Singleton
    fun provideSessionManager() = SessionManager()

}
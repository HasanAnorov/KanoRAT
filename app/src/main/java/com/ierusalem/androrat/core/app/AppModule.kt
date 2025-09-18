package com.ierusalem.androrat.core.app

import android.app.Application
import com.ierusalem.androrat.core.data.preferences.DataStorePreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(application: Application): DataStorePreferenceRepository {
        return DataStorePreferenceRepository(application)
    }

}
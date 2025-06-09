package com.ierusalem.androrat.core.app

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.ierusalem.androrat.features.link_phishing.data.LinkPhishingRepoImpl
import com.ierusalem.androrat.features.link_phishing.data.LinkPhishingRepository
import com.ierusalem.androrat.features.link_phishing.data.LinkPhishingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://4ever.uz/api/v1/"

    @Provides
    @Singleton
    fun provideChuckerInterceptor(application: Application): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(application)
            .collector(ChuckerCollector(application))
            .maxContentLength(25000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(chuckerInterceptor: ChuckerInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun providerUpdaterService(
        retrofit: Retrofit
    ): LinkPhishingService = retrofit.create(LinkPhishingService::class.java)

    @Provides
    @Singleton
    fun provideLinkPhishingRepository(
        linkPhishingService: LinkPhishingService,
    ): LinkPhishingRepository {
        return LinkPhishingRepoImpl(linkPhishingService = linkPhishingService)
    }

}
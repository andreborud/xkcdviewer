package com.andreborud.xkcdviewer.di

import com.andreborud.data.ComicApi
import com.andreborud.data.ComicRemoteDataSource
import com.andreborud.domain.GetLatestComicUseCase
import com.andreborud.domain.GetSpecificComicUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideComicApi(): ComicApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl("https://xkcd.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
            .create(ComicApi::class.java)
    }

    @Provides
    fun provideComicRemoteDataSource(comicApi: ComicApi): ComicRemoteDataSource {
        return ComicRemoteDataSource(comicApi)
    }

    @Provides
    fun provideGetLatestComicUseCase(comicRemoteDataSource: ComicRemoteDataSource): GetLatestComicUseCase {
        return GetLatestComicUseCase(comicRemoteDataSource)
    }

    @Provides
    fun provideGetSpecificComicUseCase(comicRemoteDataSource: ComicRemoteDataSource): GetSpecificComicUseCase {
        return GetSpecificComicUseCase(comicRemoteDataSource)
    }
}



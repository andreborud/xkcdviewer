package com.andreborud.xkcdviewer.di

import android.content.Context
import androidx.room.Room
import com.andreborud.data.local.ComicDao
import com.andreborud.data.local.ComicDb
import com.andreborud.data.local.ComicsLocalRepository
import com.andreborud.data.local.ComicsLocalRepositoryImpl
import com.andreborud.data.remote.ComicApi
import com.andreborud.data.remote.ComicsRemoteRepositoryImpl
import com.andreborud.domain.local.ComicLocalUseCases
import com.andreborud.domain.remote.GetLatestComicRemoteUseCase
import com.andreborud.domain.remote.GetSpecificComicRemoteUseCase
import com.andreborud.xkcdviewer.extensions.ImagePersistence
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideComicRemoteDataSource(comicApi: ComicApi): ComicsRemoteRepositoryImpl {
        return ComicsRemoteRepositoryImpl(comicApi)
    }

    @Provides
    fun provideGetLatestComicRemoteUseCase(comicsRemoteRepository: ComicsRemoteRepositoryImpl): GetLatestComicRemoteUseCase {
        return GetLatestComicRemoteUseCase(comicsRemoteRepository)
    }

    @Provides
    fun provideGetSpecificComicRemoteUseCase(comicsRemoteRepository: ComicsRemoteRepositoryImpl): GetSpecificComicRemoteUseCase {
        return GetSpecificComicRemoteUseCase(comicsRemoteRepository)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ComicDb {
        return Room.databaseBuilder(
            appContext,
            ComicDb::class.java,
            "comic_database"
        ).build()
    }

    @Provides
    fun provideComicDao(comicDb: ComicDb): ComicDao = comicDb.comicDao()

    @Provides
    fun provideComicRepository(comicDao: ComicDao): ComicsLocalRepository {
        return ComicsLocalRepositoryImpl(comicDao)
    }

    @Provides
    fun provideComicLocalUseCases(comicsLocalRepository: ComicsLocalRepository): ComicLocalUseCases {
        return ComicLocalUseCases(comicsLocalRepository)
    }

    @Provides
    fun provideComicPersistence(@ApplicationContext appContext: Context): ImagePersistence = ImagePersistence(appContext)
}
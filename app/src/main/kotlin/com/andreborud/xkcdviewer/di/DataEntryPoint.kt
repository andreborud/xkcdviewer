package com.andreborud.xkcdviewer.di

import com.andreborud.data.local.ComicDao
import com.andreborud.data.remote.ComicApi
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DataEntryPoint {
    fun provideComicApi(): ComicApi
    fun provideComicDao(): ComicDao
}
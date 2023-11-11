package com.andreborud.xkcdviewer.di

import com.andreborud.domain.GetLatestComicUseCase
import com.andreborud.domain.GetSpecificComicUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DomainEntryPoint {
    fun provideGetLatestComicsUseCase(): GetLatestComicUseCase
    fun provideGetSpecificComicUseCase(): GetSpecificComicUseCase
}
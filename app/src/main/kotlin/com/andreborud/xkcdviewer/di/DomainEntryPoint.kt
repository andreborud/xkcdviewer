package com.andreborud.xkcdviewer.di

import com.andreborud.domain.local.ComicLocalUseCases
import com.andreborud.domain.remote.GetLatestComicRemoteUseCase
import com.andreborud.domain.remote.GetSpecificComicRemoteUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DomainEntryPoint {
    fun provideGetLatestComicsRemoteUseCase(): GetLatestComicRemoteUseCase
    fun provideGetSpecificComicRemoteUseCase(): GetSpecificComicRemoteUseCase

    fun provideComicLocalUseCases(): ComicLocalUseCases
}
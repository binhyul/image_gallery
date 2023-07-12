package com.binhyul.image_gallery.data.repository

import com.binhyul.image_gallery.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataRepositoryModule {

    @Binds
    abstract fun bindAppRepo(
        repository: AppRepositoryImpl
    ): AppRepository
}
package com.binhyul.image_gallery.data.remote.di

import com.binhyul.image_gallery.data.remote.source.AppRemoteDataSource
import com.binhyul.image_gallery.data.remote.source.AppRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Singleton
    @Binds
    abstract fun bindAppRemoteDataSource(
        appRemoteDataSource: AppRemoteDataSourceImpl,
    ): AppRemoteDataSource

}
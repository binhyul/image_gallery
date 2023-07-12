package com.binhyul.image_gallery.data.remote.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.binhyul.image_gallery.data.remote.AppService
import com.binhyul.image_gallery.data.remote.model.ImageResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRemoteDataSourceImpl @Inject constructor(
    private val appService: AppService,
) : AppRemoteDataSource {
    override suspend fun getImages(): Flow<PagingData<ImageResponse>> = Pager(
        PagingConfig(
            ImageRemotePagingSource.PAGE_SIZE,
            ImageRemotePagingSource.PAGE_SIZE / 2,
            true,
            ImageRemotePagingSource.PAGE_SIZE,
        ), ImageRemotePagingSource.START
    ) {
        ImageRemotePagingSource(appService)
    }.flow


    override suspend fun getImages(start: Int, limit: Int): List<ImageResponse> =
        appService.getImageList(start, limit)
}
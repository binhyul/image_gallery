package com.binhyul.image_gallery.data.remote.source

import androidx.paging.PagingData
import com.binhyul.image_gallery.data.remote.model.ImageResponse
import kotlinx.coroutines.flow.Flow

interface AppRemoteDataSource {

    suspend fun getImages(): Flow<PagingData<ImageResponse>>

    suspend fun getImages(start: Int, limit: Int): List<ImageResponse>
}
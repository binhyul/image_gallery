package com.binhyul.image_gallery.domain.repository

import androidx.paging.PagingData
import com.binhyul.image_gallery.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getImages(): Flow<PagingData<ImageModel>>

    suspend fun getImages(start: Int, limit: Int): List<ImageModel>
}
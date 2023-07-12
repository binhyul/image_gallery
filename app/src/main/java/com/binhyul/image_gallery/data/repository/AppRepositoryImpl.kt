package com.binhyul.image_gallery.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.binhyul.image_gallery.data.remote.model.toDomain
import com.binhyul.image_gallery.data.remote.source.AppRemoteDataSource
import com.binhyul.image_gallery.domain.model.ImageModel
import com.binhyul.image_gallery.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val remoteDataSource: AppRemoteDataSource,
) : AppRepository {

    override suspend fun getImages(): Flow<PagingData<ImageModel>> =
        remoteDataSource.getImages().map { pagingData ->
            pagingData.map { imageResponse ->
                imageResponse.toDomain()
            }
        }

    override suspend fun getImages(start: Int, limit: Int): List<ImageModel> =
        remoteDataSource.getImages(start, limit).map { imageResponse ->
            imageResponse.toDomain()
        }
}

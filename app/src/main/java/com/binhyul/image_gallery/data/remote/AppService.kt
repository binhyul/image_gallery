package com.binhyul.image_gallery.data.remote

import com.binhyul.image_gallery.data.remote.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AppService {

    @GET("/photos")
    suspend fun getImageList(
        @Query("_start") start: Int,
        @Query("_limit") limit: Int,
    ): List<ImageResponse>

}


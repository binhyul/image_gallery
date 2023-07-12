package com.binhyul.image_gallery.data.remote.model

import com.binhyul.image_gallery.domain.model.ImageModel
import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName(value = "albumId") val albumId: Int,
    @SerializedName(value = "id") val id: Int,
    @SerializedName(value = "url") val url: String,
    @SerializedName(value = "title") val title: String,
    @SerializedName(value = "thumbnailUrl") val thumbnailUrl: String
)

fun ImageResponse.toDomain() = ImageModel(
    albumId, id, url, title, thumbnailUrl
)
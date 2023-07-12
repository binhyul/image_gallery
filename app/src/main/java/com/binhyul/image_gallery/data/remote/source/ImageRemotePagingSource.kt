package com.binhyul.image_gallery.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.binhyul.image_gallery.data.remote.AppService
import com.binhyul.image_gallery.data.remote.model.ImageResponse

class ImageRemotePagingSource(
    private val appService: AppService,
) : PagingSource<Int, ImageResponse>() {
    override fun getRefreshKey(state: PagingState<Int, ImageResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(PAGE_SIZE) ?: anchorPage?.nextKey?.minus(PAGE_SIZE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageResponse> {
        return loadData(params.key ?: START)
    }

    private suspend fun loadData(
        startIndex: Int
    ): LoadResult<Int, ImageResponse> {
        return try {
            val response =
                appService.getImageList(
                    start = startIndex,
                    limit = PAGE_SIZE
                )

            val prevPage = if (startIndex == START) null else startIndex - PAGE_SIZE
            val list = response.orEmpty()
            val nextPage = if (list.isEmpty()) null else startIndex + PAGE_SIZE
            val skeletonSize = if (list.isEmpty()) 0 else PAGE_SIZE

            LoadResult.Page(list, prevPage, nextPage, itemsAfter = skeletonSize)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val START = 0
        const val PAGE_SIZE = 30
    }
}
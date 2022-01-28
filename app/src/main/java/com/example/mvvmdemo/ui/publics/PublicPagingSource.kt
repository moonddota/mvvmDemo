package com.example.mvvmdemo.ui.publics

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.network.api.RequestService

class PublicPagingSource(private val gitHubService: RequestService, var id: String) :
    PagingSource<Int, ArticleBean>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleBean> {
        return try {
            val page = params.key ?: 1 // set page 1 as default
            val pageSize = params.loadSize
            val repoResponse = gitHubService.listArticle(page, id)
            val repoItems = repoResponse.data?.datas ?: emptyList()
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleBean>): Int? = null


}
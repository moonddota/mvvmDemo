package com.example.mvvmdemo.Paging3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.network.api.RequestService
import kotlinx.coroutines.flow.Flow

class PagingViewModel : BaseViewModel() {
    val PagingData = MutableLiveData<Flow<PagingData<Repo>>>()
    fun getPagingData() = launchUI(PagingData) {
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { RepoPagingSource(RequestService.instance) }
        ).flow.cachedIn(viewModelScope)
    }


}
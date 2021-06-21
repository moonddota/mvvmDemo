package com.example.mvvmdemo.ui.mine.my_score

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.RankBean
import com.example.mvvmdemo.network.api.RequestService
import kotlinx.coroutines.flow.Flow

class MyScoreVM : BaseViewModel() {

    val data = MutableLiveData<Flow<PagingData<RankBean>>>()
    fun getPagingData() = launchUI(data) {
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { RankListSource(RequestService.instance) }
        ).flow.cachedIn(viewModelScope)
    }

}
package com.example.mvvmdemo.ui.mine.share_article

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel

class ShareArticleVM : BaseViewModel() {

    private val repository = ShareArticleRepository()

    val data = MutableLiveData<Boolean>()
    fun shareArticle(title: String?, link: String?) = launchUI(data) {
        repository.shareArticle(title, link)
        true
    }


}
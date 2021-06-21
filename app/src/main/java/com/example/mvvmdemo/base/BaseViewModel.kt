package com.example.mvvmdemo.base

import androidx.lifecycle.*
import com.example.mvvmdemo.util.toast
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel(), LifecycleObserver {

    private val error by lazy { MutableLiveData<Exception>() }

    fun <T> launchUI(
            mLiveD: MutableLiveData<T>? = null,
            onError: (e: Exception) -> Unit? = {},
            block: suspend CoroutineScope.() -> T,
    ) {
        viewModelScope.launch {
            try {
                val value = withContext(Dispatchers.IO) { block() }
                mLiveD?.value = value
            } catch (e: Exception) {
                e.printStackTrace()
                onError(e)
                error.value = e
            }
        }
    }


    fun <T> launch(
        mLiveD: MutableLiveData<T>? = null,
        onError: (e: Exception) -> Unit? = {},
        IoBlock: suspend CoroutineScope.() -> T?,
        UiBlock: () -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val value = withContext(Dispatchers.IO) { IoBlock() }
                mLiveD?.value = value
                UiBlock()
            } catch (e: Exception) {
                e.printStackTrace()
                onError(e)
                toast(e.message ?: "")
                error.value = e
            }
        }
    }

    /**
     * 请求失败，出现异常
     */
    fun getError(): LiveData<Exception> {
        return error
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
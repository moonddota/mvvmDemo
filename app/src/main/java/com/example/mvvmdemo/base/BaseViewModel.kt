package com.example.mvvmdemo.base

import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils
import com.example.mvvmdemo.App
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

open class BaseViewModel : ViewModel(), LifecycleObserver {


    private val error by lazy { MutableLiveData<Exception>() }

    private val finally by lazy { MutableLiveData<Int>() }

    private val mQMUITipDialog by lazy {
        QMUITipDialog.Builder(App.getTopActivity())
            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
            .setTipWord("正在加载")
            .create()
    }

    //运行在UI线程的协程
    fun launchUI(showDialog: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch {

            try {
                withTimeout(5000) {
                    if (showDialog) mQMUITipDialog.show()
                    block()
                }
            } catch (e: Exception) {
                LogUtils.e(e)
                error.value = e
            } finally {
                if (showDialog) mQMUITipDialog.dismiss()
                finally.value = 200
            }
        }

    /**
     * 请求失败，出现异常
     */
    fun getError(): LiveData<Exception> {
        return error
    }

    /**
     * 请求完成，在此处做一些关闭操作
     */
    fun getFinally(): LiveData<Int> {
        return finally
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}
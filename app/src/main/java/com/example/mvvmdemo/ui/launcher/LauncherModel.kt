package com.example.mvvmdemo.ui.launcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.base.ResponseData
import com.example.mvvmdemo.data.*
import com.example.mvvmdemo.ui.login.LoginRepository
import com.example.mvvmdemo.util.HolderDeviceUtils
import kotlinx.coroutines.*
import java.util.*
import kotlin.system.measureTimeMillis

class LauncherModel : BaseViewModel() {

    private var data: MutableLiveData<ResponseData<FindDeviceStatusNew>> = MutableLiveData()

    private val repository = LauncherRepository()


    fun getLuncher(): LiveData<ResponseData<FindDeviceStatusNew>> {
        return data
    }

    fun launcherData() = launchUI(false) {
        val result = repository.launcherData(HolderDeviceUtils.deviceID)

        if (result.chakeData()){
            DBInstance.instance?.environmentDao?.insert(statusToEnvironment(result.tdata!!))
            data.postValue(result)
        }else{
            ToastUtils.showShort(result.message)
        }

    }


    private fun statusToEnvironment(data: FindDeviceStatusNew) = EnvironmentInfo(

        device = Device(
            deviceId = requireNotNull(data.deviceNo),
            deviceType = data.deviceType ?: DEVICE_TYPE
        ),

        enterprise = Enterprise(
            enterpriseGuid = requireNotNull(data.enterpriseGuid),
            enterpriseName = "",
            validStartTime = Date(),
            validEndTime = requireNotNull(Date())
        ),

        store = Store(
            storeGuid = requireNotNull(data.storeGuid),
            storeName = data.storeName,
            storeNumber = requireNotNull(data.storeNo)
        )
    )


}
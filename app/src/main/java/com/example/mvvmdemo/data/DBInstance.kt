package com.example.mvvmdemo.data

import androidx.room.Room
import com.example.mvvmdemo.App

object DBInstance {
    //private static final String DB_NAME = "/sdcard/LianSou/room_test.db";
    private val DB_NAME = "room_test"
    var appDataBase: AppDataBase? = null
    //下面注释表示允许主线程进行数据库操作，但是不推荐这样做。
    //我这里是为了Demo展示，稍后会结束和LiveData和RxJava的使用
    val instance: AppDataBase?
        get() {
            if (appDataBase == null) {
                synchronized(DBInstance::class.java) {
                    if (appDataBase == null) {
                        appDataBase = Room.databaseBuilder(
                            App.instance,
                            AppDataBase::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return appDataBase
        }
}
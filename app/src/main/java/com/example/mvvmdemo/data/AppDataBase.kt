package com.example.mvvmdemo.data

import androidx.room.Database
import androidx.room.RoomDatabase

//注解指定了database的表映射实体数据以及版本等信息(后面会详细讲解版本升级)
@Database(entities = [EnvironmentInfo::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract val environmentDao: EnvironmentDao

}

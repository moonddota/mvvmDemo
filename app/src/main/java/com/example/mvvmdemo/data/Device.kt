package com.example.mvvmdemo.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "device")
data class Device(

    /**
     * 逻辑ID
     */
    @PrimaryKey
    @ColumnInfo(name = "device_id")
    var deviceId: String,

    /**
     * 设备类型
     */
    @ColumnInfo(name = "device_type")
    var deviceType: Int? = null

) : Parcelable
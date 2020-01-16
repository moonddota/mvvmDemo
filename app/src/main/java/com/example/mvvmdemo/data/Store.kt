package com.example.mvvmdemo.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
@Entity(tableName = "store")
data class Store(

    /**
     * 组织GUID
     */
    @PrimaryKey
    @ColumnInfo(name = "store_guid")
    var storeGuid: String,

    /**
     * 组织名称
     */
    @ColumnInfo(name = "store_name")
    var storeName: String? = null,

    /**
     * 组织编码
     */
    @SerialName("id")
    @ColumnInfo(name = "storeNumber")
    var storeNumber: String? = null

) : Parcelable
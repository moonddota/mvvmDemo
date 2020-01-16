package com.example.mvvmdemo.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
@Entity(tableName = "user", indices = [Index(value = ["user_guid"], name = "idx_user_guid")])
data class User(

    /**
     * 登录用户GUID
     */
    @PrimaryKey
    @ColumnInfo(name = "user_guid")
    var userGuid: String,

    /**
     * 登录用户名称
     */
    @SerialName("name")
    @ColumnInfo(name = "user_name")
    var userName: String? = null,

    /**
     * 登录用户帐号
     */
    @SerialName("account")
    @ColumnInfo(name = "user_account")
    var userAccount: String? = null,

    /**
     * 登录用户手机号
     */
    @SerialName("tel")
    @ColumnInfo(name = "user_tel")
    var userTel: String? = null

) : Parcelable
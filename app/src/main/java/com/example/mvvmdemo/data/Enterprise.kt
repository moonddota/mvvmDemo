package com.example.mvvmdemo.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Parcelize
@Serializable
@Entity(tableName = "enterprise")
data class Enterprise(

    /**
     * 企业GUID
     */
    @PrimaryKey
    @ColumnInfo(name = "enterprise_guid")
    var enterpriseGuid: String,

    /**
     * 商户编号
     */
    @SerialName("uid")
    @ColumnInfo(name = "enterprise_uid")
    var enterpriseUid: Long? = null,

    /**
     * 企业名字
     */
    @SerialName("name")
    @ColumnInfo(name = "enterprise_name")
    var enterpriseName: String? = null,

    /**
     * 注册电话
     */
    @SerialName("regTel")
    @ColumnInfo(name = "enterprise_reg_tel")
    var enterpriseRegTel: String? = null,

    /**
     * 注册邮箱
     */
    @SerialName("regEmail")
    @ColumnInfo(name = "enterprise_reg_email")
    var enterpriseRegEmail: String? = null,

    /**
     * 注册地址
     */
    @SerialName("address")
    @ColumnInfo(name = "enterprise_address")
    var enterpriseAddress: String? = null,

    /**
     * 有效起始时间
     */
    @ColumnInfo(name = "valid_start_time")
    @ContextualSerialization
    var validStartTime: Date? = null,

    /**
     * 有效截止时间
     */
    @ColumnInfo(name = "valid_end_time")
    @ContextualSerialization
    var validEndTime: Date? = null

) : Parcelable
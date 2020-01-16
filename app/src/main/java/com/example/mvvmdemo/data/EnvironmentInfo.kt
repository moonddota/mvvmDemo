package com.example.mvvmdemo.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "environment_info")
data class EnvironmentInfo(

    /**
     * 该表只有一条数据，故设置id为固定0
     */
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "token")
    var token: String? = null,

    @Embedded
    var enterprise: Enterprise? = null,

    @Embedded
    var store: Store? = null,

    @Embedded
    var device: Device? = null,

    @Embedded
    var user: User? = null
) : Parcelable {
    companion object {
        fun empty(): EnvironmentInfo {
            return EnvironmentInfo(
                token = "",
                device = Device(
                    deviceId = "", // 云端生成的id
                    deviceType = DEVICE_TYPE
                ),
                enterprise = Enterprise(
                    enterpriseGuid = "",
                    enterpriseName = "",
                    validStartTime = Date(),
                    validEndTime = Date()
                ),
                store = Store(
                    storeGuid = "",
                    storeName = ""
                ),
                user = User(
                    userGuid = "0",
                    userName = "",
                    userTel = "",
                    userAccount = ""
                )
            )
        }
    }
}
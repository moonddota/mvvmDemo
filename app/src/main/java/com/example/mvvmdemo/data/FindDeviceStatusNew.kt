package com.example.mvvmdemo.data


import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import kotlinx.serialization.Optional
import java.util.*

/**
 *   正餐   根据设备Id查询设备的注册、绑定状态  返回的实体
 */

@Serializable
@Parcelize
data class FindDeviceStatusNew(
        /**
         * 设备是否绑定
         */
        @Optional
        var binding: Boolean? = null,

        /**
         * 创建人guid
         */
        @Optional
        var createUserGuid: String? = null,

        /**
         * 创建人姓名
         */
        @Optional
        var createUserName: String? = null,

        /**
         *         系统设备编号（云端生成）
         */
        @Optional
        var deviceGuid: String? = null,

        /**
         * 设备类型名称
         */
        @Optional
        var deviceName: String? = null,

        /**
         * 厂商设备编号
         */
        @Optional
        var deviceNo: String? = null,

        /**
         * PC服务端- 0、PC平板- 1、小店通- 2、一体机- 3、POS机- 4、云平板- 5、点菜宝(M1)- 6、PV1(带刷卡的点菜宝)- 7
         */
        @Optional
        var deviceType: Int? = null,
        /**
         * 关联的企业guid
         */
        @Optional
        var enterpriseGuid: String? = null,

        /**
         * 创建时间
         */
        @Optional
        @ContextualSerialization
        var gmtCreate: Date? = null,

        /**
         * 更新时间
         */
        @Optional
        @ContextualSerialization
        var gmtModified: Date? = null,

        /**
         * 设备解绑时间
         */
        @Optional
        @ContextualSerialization
        var gmtUnbind: Date? = null,

        /**
         * 设备是否已在云端录入
         */
        @Optional
        var register: Boolean? = null,

        /**
         * 打印设备排序（仅设备类型为一体机有效，默认为0。）
         */
        @Optional
        var sort: Int? = null,
        /**
         * 门店guid
         */
        @Optional
        var storeGuid: String? = null,

        /**
         * 门店名称
         */
        @Optional
        var storeName: String? = null,

        /**
         * 门店编号
         */
        @Optional
        var storeNo: String? = null

) : Parcelable {

}
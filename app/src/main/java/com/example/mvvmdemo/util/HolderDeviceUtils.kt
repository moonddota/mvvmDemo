package com.example.mvvmdemo.util

import android.annotation.SuppressLint
import android.content.Context.TELEPHONY_SERVICE
import android.provider.Settings
import android.telephony.TelephonyManager
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.EncryptUtils
import com.example.mvvmdemo.App

object HolderDeviceUtils {
    /**商米名牌
     * P系列支持银行卡
     * V,P系列支持打印
     * L,M系列不支持打印
     *
     * 商米产品： https://sunmi.com/products/
     * */
    const val BRAND_SUNMI = "SUNMI"
    const val BRAND_SUNMI_L2_SUPPORT_INFRARED_SCANNING = "L203"
    //----------------2018-12-03  P1 V1 M1 D1已经停产不在产品适配范围内---------------------
    const val BRAND_SUNMI_MODEL_M1 = "M1"
    const val BRAND_SUNMI_MODEL_V1 = "V1"
    const val BRAND_SUNMI_MODEL_P1 = "P1"
    //----------------2018-12-03  P1 V1 M1 D1已经停产不在产品适配范围内---------------------
    const val BRAND_SUNMI_MODEL_M2 = "M2"
    const val BRAND_SUNMI_MODEL_P1N = "P1N"
    const val BRAND_SUNMI_MODEL_P2 = "P2"
    const val BRAND_SUNMI_MODEL_V1S = "V1s"
    const val BRAND_SUNMI_MODEL_V2 = "V2"
    const val BRAND_SUNMI_MODEL_V2_MF = "V2-MF"
    const val BRAND_SUNMI_MODEL_V2PRO = "V2Pro"
    const val BRAND_SUNMI_MODEL_L2 = "L2"
    /**
     * 获取设备ID(设备硬件编号)
     */
    val deviceID: String by lazy {
        val c = Class.forName("android.os.SystemProperties")
        val get = c.getMethod("get", String::class.java)
        val deviceId = get.invoke(c, "ro.serialno") as String?
        return@lazy if (deviceId == null || deviceId.isBlank() || (!sunmi)) uniqueID else deviceId
    }

    /**
     * 由于模拟器不一定存在设备ID，所以采用 imei + adnroidId + macAddress 拼接得到一个唯一的id比较好
     */
    val uniqueID: String by lazy {
        val context = App.instance
        val telephonyManager = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        val imei = telephonyManager.deviceId
        val androidId =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        val macAddress = DeviceUtils.getMacAddress()
        val longIdBuilder = StringBuilder()
        if (imei != null) {
            longIdBuilder.append(imei)
        }
        if (androidId != null) {
            longIdBuilder.append(androidId)
        }
        if (macAddress != null) {
            longIdBuilder.append(macAddress)
        }
        EncryptUtils.encryptMD5ToString(longIdBuilder.toString())
    }

    /**
     * 品牌  商米的品牌名统一为 SUNMI
     */
    val brand: String? by lazy {
        return@lazy System.getProperty("ro.product.brand")
//        return@lazy SystemProperties.get("ro.product.brand")
    }
    /**
     * 仅当brand为SUNMI 下生效
     * 设备的系统型号 model( V、M、P、L开头为手持设备)
     */
    val model: String? by lazy {
        System.getProperty("ro.product.model")
//        return@lazy SystemProperties.get("ro.product.model")
    }
    /**
     * 获取ROM版本号
     */
    val versionname: String? by lazy {
        System.getProperty("ro.version.sunmi_versionname")
//        return@lazy SystemProperties.get("ro.version.sunmi_versionname")
    }
    /**
     *获取ROM顺序号
     */
    val versioncode: String? by lazy {
        System.getProperty("ro.version.sunmi_versioncode")
//        return@lazy SystemProperties.get("ro.version.sunmi_versioncode")
    }
    val sunmi: Boolean by lazy {
        BRAND_SUNMI.equals(brand, true)
    }
    /**
     *是否支持打印
     */
    val supportPrint: Boolean by lazy {
        if (BRAND_SUNMI.equals(brand, true) &&
            (BRAND_SUNMI_MODEL_P1.equals(model, true)
                    || BRAND_SUNMI_MODEL_P1N.equals(model, true)
                    || model?.startsWith(BRAND_SUNMI_MODEL_P2, true) == true
                    || BRAND_SUNMI_MODEL_V1.equals(model, true)
                    || BRAND_SUNMI_MODEL_V1S.equals(model, true)
                    || BRAND_SUNMI_MODEL_V2.equals(model, true)
                    || BRAND_SUNMI_MODEL_V2_MF.equals(model, true)
                    || BRAND_SUNMI_MODEL_V2PRO.equals(model, true))
        ) {
            return@lazy true
        }
        false
    }
    /**
     * 是否支持银行卡功能 目前商米P系列支持（芯片  nfc   磁条）
     */
    val supportBankCard: Boolean by lazy {
        if (BRAND_SUNMI.equals(brand, true) && (
                    BRAND_SUNMI_MODEL_P1.equals(model, true)
                            || BRAND_SUNMI_MODEL_P1N.equals(model, true)
                            || model?.startsWith(BRAND_SUNMI_MODEL_P2, true) == true)
        ) {
            return@lazy true
        }
        false
    }
    /**
     * 是否支持红外扫描头功能 （条形码和二维码）
     * L2：L201不带红外扫描头   L203带红外扫描头
     */
    val supportInfraredScanning: Boolean by lazy {
        if (BRAND_SUNMI.equals(brand, true)
            && (BRAND_SUNMI_MODEL_L2.equals(model, true) && deviceID.startsWith(
                BRAND_SUNMI_L2_SUPPORT_INFRARED_SCANNING,
                true
            ))
        ) {
            return@lazy true
        }
        false
    }
}
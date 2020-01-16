package com.example.mvvmdemo.data

import com.example.mvvmdemo.util.HolderDeviceUtils

/**
 * Author: lcl <br/>
 * Date: 2018/9/26<br/>
 * Description <br/>
 *  快餐的模块的枚举类型
 */


val DEVICE_TYPE: Int   by lazy {
    if (HolderDeviceUtils.supportPrint) {
        if (HolderDeviceUtils.supportBankCard) {
            return@lazy DeviceTypeEnum.PV1.code
        }
        return@lazy DeviceTypeEnum.POS.code
    } else {
        return@lazy DeviceTypeEnum.M1.code
    }
}

/**
 * 设备类型(死数据)，只是应用于登录双方数据同步
 * 设备访问类型
 * sundy 2019-02-13 将此枚举私有化 （原因设备类型不固定可能为M1 P1 V1 M2 MOBILE）--->使用 [com.holderzone.android.store.pos.data.domain.dto.enums.DEVICE_TYPE]替代
 */
private enum class DeviceTypeEnum(val code: Int) {

    /**
     * PC服务端- 0
     */
    PC_WEB(0),

    /**
     * PC平板- 1
     */
    PC_PAD(1),

    /**
     * 小店通- 2
     */
    SHOP_PASS(2),

    /**
     * 一体机- 3
     */
    AIO(3),

    /**
     * POS机- 4
     */
    POS(4),

    /**
     * 云平板- 5
     */
    PAD(5),

    /**
     * 点菜宝(M1)- 6
     */
    M1(6),

    /**
     * PV1(带刷卡的点菜宝)- 7
     */
    PV1(7);
}

/**
 * 订单来源的类型枚举(提交订单使用到)
 */
@Deprecated("""
    弃用时间:2018.11.16
    弃用原因:订单和登录[DeviceTypeEnum] 相同的一个来源存在不同的值。差异化严重不好区分，所以经过讨论后决定将现在
    订单的来源类型进行弃用。在[DeviceTypeEnum]中的类型保持一致，故作此说明
    注意:类会继续保持但不建议使用，建议使用[DeviceTypeEnum]枚举类型进行代替
""")
enum class OrderSourceEnum(val code: Int) {

    /**
     * 一体机点餐下单
     */
    AIO(1),

    /**
     * POS设备下单
     */
    POS(2),

    /**
     * M1设备
     */
    M1(3),

    /**
     * 平板设备
     */
    PAD(4),

    /**
     * 微信公众号
     */
    WEI_XIN(5),

    /**
     * 美团外卖
     */
    MEI_TUAN(6),

    /**
     * 饿了么
     */
    E_LE_ME(7)
}

/**
 * 订单交易模式
 */
enum class OrderTradeModeEnum(val code: Int) {
    /**
     * 堂食(正餐)
     */
    DINE(0),

    /**
     * 快餐(其中快餐默认为快餐堂吃)
     */
    SNACK(1),

    /**
     * 外卖
     */
    TAKEAWAY(2),

    /**
     * 快餐的外卖
     */
    SNACK_TAKEAWAY(3)
}

/**
 * 订单的商品实体的商品类型
 */
enum class OrderSnackDishTypeEnum(val code: Int) {

    /**
     * 单规格
     */
    SINGLE_SPEC(0),

    /**
     * 多规格
     */
    MULTI_SPEC(1),

    /**
     * 套餐主项
     */
    PACK_MAIN(2),

    /**
     * 套餐子项
     */
    PACK_SUB(3),

    /**
     * 称重商品
     */
    WEIGHING(4)
}

/**
 * 订单详情页面到快餐页面的类型枚举
 */
enum class OrderDetile2SnackType(val type: Int) {
    /** 反结账订单 */
    RECHECK_ORDER(0),
    /** 修改订单 */
    UPDATE_ORDER(1),
    /** 结账订单 */
    BILL_ORDER(2),
}

/**
 * 订单详情页面到快餐页面的类型枚举
 */
enum class SnackDishBillType(val type: Int) {
    /** 未结账，商品业务实体的isPlay字段 */
    NOT_BILL_IS_PLAY(0),
    /** 已结账,表示商品是反结账商品的标志 */
    FINISH_BILL_IS_PLAY(1)
}





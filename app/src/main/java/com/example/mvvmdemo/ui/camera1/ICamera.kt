package com.example.mvvmdemo.ui.camera1

import android.app.Activity
import android.graphics.SurfaceTexture

interface ICamera {
    /**
     * 打开相机
     */
    fun openCamera(activity: Activity, surfaceTexture: SurfaceTexture)

    /**
     * 打开相机
     */
    fun openCamera(facing: Int, activity: Activity, surfaceTexture: SurfaceTexture)

    /**
     * 关闭相机
     */
    fun closeCamera()

    /**
     * 切换相机
     */
    fun switchCamera()

    /**
     * 切换相机
     */
    fun switchCamera(facing: Int)

    /**
     * 设置Facing
     */
    fun setCameraFacing(facing: Int)

    /**
     * 获取Facing
     */
    fun getCameraFacing() :Int

    /**
     * 设置预览尺寸
     */
    fun setPreviewSize(cameraSize: Size)

    /**
     * 获取预览尺寸
     */
    fun getPreviewSize(): Size

    /**
     * 设置显示旋转角度
     */
    fun setDisplayOrientation(displayOrientation: Int)

    /**
     * 获取显示旋转角度
     */
    fun getDisplayOrientation(): Int

    /**
     * 释放相机
     */
    fun releaseCamera()

    /**
     * 添加相机回调
     */
    fun addOnCameraListener(onCameraListener: OnCameraListener)

    /**
     * 移除相机回调
     */
    fun removeOnCameraListener(onCameraListener: OnCameraListener)

    /**
     * 移除所有回调
     */
    fun removeAllOnCameraListener()

}
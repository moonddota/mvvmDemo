package com.example.mvvmdemo.ui.camera1

interface OnCameraListener {
    /**
     * 相机打开
     */
    fun onCameraOpened(cameraSize: Size, facing: Int)

    /**
     * 相机关闭
     */
    fun onCameraClosed()

    /**
     * 相机异常
     */
    fun onCameraError(e: Exception)
}
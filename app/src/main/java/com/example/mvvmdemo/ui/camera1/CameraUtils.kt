package com.example.mvvmdemo.ui.camera1

import android.app.Activity
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.view.Surface

object CameraUtils {
    /**
     * 获取摄像头旋转角度
     */
    fun getDisplayOrientation(activity:Activity,facing :Int):Int{
        val info = CameraInfo()
        Camera.getCameraInfo(facing, info)
        val rotation = activity.windowManager.defaultDisplay
            .rotation
        var degrees = 0
        when (rotation) {
            Surface.ROTATION_0 -> degrees = 0
            Surface.ROTATION_90 -> degrees = 90
            Surface.ROTATION_180 -> degrees = 180
            Surface.ROTATION_270 -> degrees = 270
        }
        var result: Int
        if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360
            result = (360 - result) % 360 // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360
        }
        return result
    }

    /**
     * 寻找最合适的尺寸
     */
    fun findTheBestSize(sizeList :List<Camera.Size>,screenW:Int,screenH:Int):Camera.Size{
        require(!(sizeList == null || sizeList.isEmpty()))

        var bestSize: Camera.Size? = sizeList[0]
        for (size in sizeList) {
            val width = size.height
            val height = size.width
            val ratioW = width.toFloat() / screenW
            val ratioH = height.toFloat() / screenH
            if (ratioW == ratioH) {
                bestSize = size
                break
            }
        }
        return bestSize!!
    }

}
package com.example.mvvmdemo.ui.camera1

import android.app.Activity
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.hardware.Camera.PreviewCallback
import java.io.IOException
import java.lang.Exception
import java.lang.IllegalStateException
import java.util.ArrayList

class Camera1 : ICamera {

    /**
     * Camera
     */
    private var camera: Camera? = null

    /**
     * 预览尺寸
     */
    private var cameraSize = Size(-1, -1)

    /**
     * 默认摄像头方向
     */
    private var facing: Int = CameraConstants.facing.BACK

    /**
     * 旋转角度
     */
    private var displayOrientation = -1

    /**
     * 相机回调
     */
    private val onCameraListenerList by lazy { arrayListOf<OnCameraListener>() }


    override fun openCamera(activity: Activity, surfaceTexture: SurfaceTexture) {
        openCamera(facing, activity, surfaceTexture)
    }

    override fun openCamera(facing: Int, activity: Activity, surfaceTexture: SurfaceTexture) {

        // 先关闭相机
        closeCamera()

        // 判断是否存在摄像头
        val cameraNum = Camera.getNumberOfCameras()
        if (cameraNum <= 0) {
            onCameraError(IllegalStateException("camera num <= 0"))
            return
        }

        // 检查传入的facing
        var cameraIndex = -1
        if (facing == CameraConstants.facing.BACK) {
            cameraIndex = Camera.CameraInfo.CAMERA_FACING_BACK
        } else if (facing == CameraConstants.facing.FRONT) {
            cameraIndex = Camera.CameraInfo.CAMERA_FACING_FRONT
        }
        if (cameraIndex == -1) {
            onCameraError(IllegalStateException("camera facing exception"))
            return
        }

        // 判断摄像头个数，以决定使用哪个打开方式
        camera = if (cameraNum >= 2) {
            Camera.open(cameraIndex)
        } else {
            Camera.open()
        }

        // 判断Camera是否初始化成功
        if (camera == null) {
            onCameraError(IllegalStateException("camera is null"))
            return
        }

        this.facing = facing

        try {
            // 获取摄像头参数
            val parameters = camera!!.getParameters()
            val previewSizeList = parameters.supportedPreviewSizes
            if (cameraSize.width <= 0 || cameraSize.height <= 0) {
                val size: Camera.Size = CameraUtils.findTheBestSize(
                    previewSizeList,
                    activity.resources.displayMetrics.widthPixels,
                    activity.resources.displayMetrics.heightPixels
                )
                cameraSize.width = size.width
                cameraSize.height = size.height
            }

            // 设置预览尺寸
            parameters.setPreviewSize(cameraSize.width, cameraSize.height)

            // 这里设置使用的对焦模式
            if (this.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO
            }

            // 设置摄像头参数
            camera!!.setParameters(parameters)
            camera!!.setPreviewTexture(surfaceTexture)
            if (displayOrientation < 0) {
                displayOrientation = CameraUtils.getDisplayOrientation(activity, cameraIndex)
            }
            camera!!.setDisplayOrientation(displayOrientation)
            camera!!.setOneShotPreviewCallback(PreviewCallback { data, camera ->
                onCameraOpened(
                    cameraSize,
                    this@Camera1.facing
                )
            })
            camera!!.startPreview()
        } catch (e: IOException) {
            onCameraError(e)
        }
    }

    override fun closeCamera() {
        if (camera == null) {
            return
        }
        camera!!.stopPreview()
        camera!!.release()
        camera = null
        onCameraClosed()
    }

    override fun switchCamera() {
        facing = if (facing == CameraConstants.facing.BACK) {
            CameraConstants.facing.FRONT
        } else {
            CameraConstants.facing.BACK
        }
    }

    override fun switchCamera(facing: Int) {
        this.facing = facing
    }

    override fun setCameraFacing(facing: Int) {
        this.facing = facing
    }

    override fun getCameraFacing(): Int {
        return facing
    }

    override fun setPreviewSize(cameraSize: Size) {
        this.cameraSize = cameraSize
    }

    override fun getPreviewSize(): Size {
        return cameraSize
    }

    override fun setDisplayOrientation(displayOrientation: Int) {
        this.displayOrientation = displayOrientation
    }

    override fun getDisplayOrientation(): Int {
        return displayOrientation
    }

    override fun releaseCamera() {
        closeCamera()
        removeAllOnCameraListener()
    }

    override fun addOnCameraListener(onCameraListener: OnCameraListener) {
        onCameraListenerList.add(onCameraListener)
    }

    override fun removeOnCameraListener(onCameraListener: OnCameraListener) {
        onCameraListenerList.remove(onCameraListener)
    }

    override fun removeAllOnCameraListener() {

    }

    /**
     * 相机打开回调
     */
    private fun onCameraOpened(cameraSize: Size, facing: Int) {
        if (onCameraListenerList.isEmpty()) {
            return
        }
        for (listener in onCameraListenerList) {
            listener.onCameraOpened(cameraSize, facing)
        }
    }

    /**
     * 相机关闭回调
     */
    private fun onCameraClosed() {
        if (onCameraListenerList.isEmpty()) {
            return
        }
        for (listener in onCameraListenerList) {
            listener.onCameraClosed()
        }
    }


    /**
     * 相机错误回调
     */
    private fun onCameraError(error: Exception) {
        if (onCameraListenerList.isEmpty()) {
            return
        }
        for (listener in onCameraListenerList) {
            listener.onCameraError(error)
        }
    }
}
package com.example.mvvmdemo.ui.camera1

import android.app.Activity
import android.graphics.SurfaceTexture
import android.os.Handler
import android.os.HandlerThread

class CameraManager : ICamera {
    /**
     * Camera实现
     */
    private val camera: ICamera = Camera1()

    /**
     * 后台线程
     */
    private var handler: Handler? = null
    private var thread: HandlerThread? = null
    override fun openCamera(activity: Activity, surfaceTexture: SurfaceTexture) {
        postTask { camera.openCamera(activity, surfaceTexture) }
    }

    override fun openCamera(facing: Int, activity: Activity, surfaceTexture: SurfaceTexture) {
        postTask { camera.openCamera(facing, activity, surfaceTexture) }
    }

    override fun closeCamera() {
        postTask { camera.closeCamera() }
    }

    override fun switchCamera() {
        postTask { camera.switchCamera() }
    }

    override fun switchCamera(facing: Int) {
        postTask { camera.switchCamera(facing) }
    }

    override fun setCameraFacing(facing: Int) {
        postTask { camera.setCameraFacing(facing) }
    }

    override fun getCameraFacing(): Int {
        return camera.getCameraFacing()
    }

    override fun setPreviewSize(cameraSize: Size) {
        postTask { camera.setPreviewSize(cameraSize) }
    }

    override fun getPreviewSize(): Size {
        return camera.getPreviewSize()
    }

    override fun setDisplayOrientation(displayOrientation: Int) {
        postTask { camera.setDisplayOrientation(displayOrientation) }
    }

    override fun getDisplayOrientation(): Int {
        return camera.getDisplayOrientation()
    }

    override fun releaseCamera() {
        if (handler == null) {
            return
        }
        postTask {
            camera.releaseCamera()
            stopBackground()
        }
    }

    override fun addOnCameraListener(onCameraListener: OnCameraListener) {
        postTask { camera.addOnCameraListener(onCameraListener) }
    }

    override fun removeOnCameraListener(onCameraListener: OnCameraListener) {
        postTask { camera.removeOnCameraListener(onCameraListener) }
    }

    override fun removeAllOnCameraListener() {
        postTask { camera.removeAllOnCameraListener() }
    }

    /**
     * 获取Handler
     */
    private fun getHandler(): Handler? {
        if (thread == null || handler == null) {
            startBackground()
        }
        return handler
    }

    /**
     * 开启线程
     */
    private fun startBackground() {
        stopBackground()
        thread = HandlerThread("camera_manager")
        thread!!.start()
        handler = Handler(thread!!.looper)
    }

    /**
     * 关闭线程
     */
    private fun stopBackground() {
        if (thread != null) {
            thread!!.quitSafely()
            thread = null
        }
        if (handler != null) {
            handler = null
        }
    }

    /**
     * 执行任务
     */
    private fun postTask(runnable: Runnable) {
        getHandler()!!.post(runnable)
    }
}
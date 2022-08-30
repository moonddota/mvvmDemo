package com.example.mvvmdemo.ui.camera1

import android.graphics.SurfaceTexture
import android.view.TextureView.SurfaceTextureListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.constant.RouterActivityPath
import com.example.mvvmdemo.databinding.ActivityCamer1Binding

@Route(path = RouterActivityPath.Camera_1.Ac)
class Camera1Ac : BaseViewModelActivity<Camera1VM, ActivityCamer1Binding>() {

    private val cameraManager = CameraManager()


    override fun initView() {
        binding.btnSwitch.setOnClickListener {
            switchCamera()
        }
    }

    override fun initData() {

    }

    override fun providerVMClass(): Class<Camera1VM> = Camera1VM::class.java

    override fun onResume() {
        super.onResume()
        openCamera()
    }

    override fun onPause() {
        super.onPause()
        closeCamera()
    }

    private fun openCamera() {
        if (binding.textureView.isAvailable) {
            cameraManager.openCamera(
                this,
                binding.textureView.surfaceTexture!!
            )
        } else {
            binding.textureView.surfaceTextureListener = object : SurfaceTextureListener {
                override fun onSurfaceTextureAvailable(
                    surface: SurfaceTexture,
                    width: Int,
                    height: Int
                ) {
                    cameraManager.openCamera(this@Camera1Ac, surface)
                }

                override fun onSurfaceTextureSizeChanged(
                    surface: SurfaceTexture,
                    width: Int,
                    height: Int
                ) {
                }

                override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                    return true
                }

                override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
            }
        }
    }

    private fun closeCamera() {
        cameraManager.closeCamera()
    }

    private fun switchCamera() {
        cameraManager.switchCamera()
        openCamera()
    }
}



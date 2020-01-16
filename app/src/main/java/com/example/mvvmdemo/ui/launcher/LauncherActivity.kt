package com.example.mvvmdemo.ui.launcher

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.ui.login.LoginActivity
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import kotlinx.coroutines.delay
import com.jeremyliao.liveeventbus.LiveEventBus
import androidx.core.app.ComponentActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import com.example.mvvmdemo.R
import com.example.mvvmdemo.data.DBInstance
import com.example.mvvmdemo.util.HolderDeviceUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class LauncherActivity : BaseViewModelActivity<LauncherModel>(),
    EasyPermissions.PermissionCallbacks {

    companion object {
        //申请权限唯一标识码
        private const val CALL_PHONE = 0x01
    }

    private val REQUEST_REQUESTSPECIALPERMISSION: Int = 1

    override fun getLayoutId(): Int = com.example.mvvmdemo.R.layout.launcher_activity

    override fun providerVMClass(): Class<LauncherModel>? = LauncherModel::class.java

    override fun initView() {

    }

    override fun initData() {

        viewModel.getLuncher().observe(this, Observer {

            if (!(it.tdata?.register ?: false)) {

                QMUIDialog.MessageDialogBuilder(this)
                    .setMessage(
                        getString(
                            R.string.not_registered_device,
                            HolderDeviceUtils.deviceID
                        )
                    )
                    .addAction(
                        0,
                        "去注册",
                        QMUIDialogAction.ACTION_PROP_NEGATIVE,
                        object : QMUIDialogAction.ActionListener {
                            override fun onClick(dialog: QMUIDialog?, index: Int) {
                                dialog?.dismiss()
                                System.exit(0)
                            }
                        })
                    .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
                return@Observer
            }

            if (it.tdata?.binding ?: false) {
                startActivity(LoginActivity.getLoginActivity(this))
            } else {
                toast("未绑定")
            }
        })

        requestCallPhonePermission()

        LiveEventBus
            .get("key_name", String::class.java)
            .observe(this, Observer {

                LogUtils.e(it)
            })
    }

    @AfterPermissionGranted(CALL_PHONE)
    fun requestCallPhonePermission() {
        val perms = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
        if (EasyPermissions.hasPermissions(this, *perms)) {
            viewModel.launcherData()
        } else {
            EasyPermissions.requestPermissions(this, "xxx请求拨权限！", CALL_PHONE, *perms)

            GlobalScope.launch {
                toast("系统仍需悬浮对话框权限，请注意勾选，3秒后跳转前往！")
                delay(3000L)
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName())
                )
                startActivityForResult(intent, REQUEST_REQUESTSPECIALPERMISSION)
            }
        }
    }

    //权限同意
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        viewModel.launcherData()
    }

    //权限拒绝
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            showDialog()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun showDialog() {
        QMUIDialog.MessageDialogBuilder(this)
            .setTitle("警告")
            .setMessage("需要开启权限才能使用")
            .addAction("取消", object : QMUIDialogAction.ActionListener {
                override fun onClick(dialog: QMUIDialog?, index: Int) {
                    dialog?.dismiss();
                }

            })
            .addAction(
                0,
                "去开启",
                QMUIDialogAction.ACTION_PROP_NEGATIVE,
                object : QMUIDialogAction.ActionListener {
                    override fun onClick(dialog: QMUIDialog?, index: Int) {
                        requestCallPhonePermission()
                        dialog?.dismiss()
                    }
                })
            .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

}


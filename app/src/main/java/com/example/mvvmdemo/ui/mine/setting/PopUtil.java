package com.example.mvvmdemo.ui.mine.setting;

import android.os.Handler;
import android.os.Looper;

public class PopUtil {

    public static void show(String msg) {
        show(msg, null);
    }

    public static void show(String msg, OnPopCallBack onPopCallBack) {
        CommonPop commonPop = new CommonPop(msg);
        commonPop.showPopupWindow();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (commonPop != null && commonPop.isShowing()) {
                    commonPop.dismiss();
                }
                if (onPopCallBack != null) {
                    onPopCallBack.dismiss();
                }
            }
        }, 2000);
    }

    public interface OnPopCallBack {
        void dismiss();
    }
}
package com.example.mvvmdemo.util

import android.content.Context
import android.content.SharedPreferences
import java.lang.reflect.Method

object SharePreUtil {
    private var mSp: SharedPreferences? = null

    /**
     * 在Application中初始化
     */
    fun init(context: Context, spName: String) {
        mSp = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    /**
     * 保存
     */
    fun save(key: String, value: Any) {
        val editor = mSp?.edit()
        if (value is String) {
            editor?.putString(key, value)
        } else if (value is Int) {
            editor?.putInt(key, value)
        } else if (value is Boolean) {
            editor?.putBoolean(key, value)
        } else if (value is Float) {
            editor?.putFloat(key, value)
        } else if (value is Long) {
            editor?.putLong(key, value)
        } else {
            editor?.putString(key, value.toString())
        }

        editor?.let { SharedPreferencesCompat().apply(it) }
    }

    /**
     * 取值
     */
    operator fun get(key: String, defaultValue: Any): Any? {
        return if (defaultValue is String) {
            mSp?.getString(key, defaultValue)
        } else if (defaultValue is Int) {
            mSp?.getInt(key, defaultValue)
        } else if (defaultValue is Boolean) {
            mSp?.getBoolean(key, defaultValue)
        } else if (defaultValue is Float) {
            mSp?.getFloat(key, defaultValue)
        } else if (defaultValue is Long) {
            mSp?.getLong(key, defaultValue)
        } else {
            null
        }
    }

    /**
     * 删除
     */
    fun remove(key: String) {
        val editor = mSp?.edit()
        editor?.remove(key)

        editor?.let { SharedPreferencesCompat().apply(it) }
    }

    /**
     * 清空
     */
    fun clear() {
        val editor = mSp?.edit()
        editor?.clear()

        editor?.let { SharedPreferencesCompat().apply(it) }
    }

    /**
     * 查询key是否存在
     */
    fun contain(key: String): Boolean? {
        return mSp?.contains(key)
    }

    /**
     * 自定义提交方法
     */
    private class SharedPreferencesCompat {

        val sApplyMethod = findApplyMethod()

        private fun findApplyMethod(): Method? {
            try {
                val clz = SharedPreferences.Editor::class.java
                return clz.getMethod("apply")
            } catch (e: NoSuchMethodException) {
            }

            return null
        }

        fun apply(editor: SharedPreferences.Editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor)
                    return
                }
            } catch (e: Exception) {
            }

            editor.commit()
        }
    }
}
package com.example.mvvmdemo.widget;

public interface Renderer {
    void onCreate();

    void onChange(int width, int height);

    void onDraw();
}

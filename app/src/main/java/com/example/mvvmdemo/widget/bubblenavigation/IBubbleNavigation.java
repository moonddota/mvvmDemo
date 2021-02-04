package com.example.mvvmdemo.widget.bubblenavigation;

import android.graphics.Typeface;

import com.example.mvvmdemo.widget.bubblenavigation.listener.BubbleNavigationChangeListener;


public interface IBubbleNavigation {
    void setNavigationChangeListener(BubbleNavigationChangeListener navigationChangeListener);

    void setTypeface(Typeface typeface);

    int getCurrentActiveItemPosition();

    void setCurrentActiveItem(int position);

    void setBadgeValue(int position, String value);
}

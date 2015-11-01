package com.tatteam.languagerealm.app;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by Shu on 10/19/2015.
 */
public class ViewPagerNonSwipe extends ViewPager {
    public ViewPagerNonSwipe(Context context) {
        super(context);
    }

    public ViewPagerNonSwipe(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        // Never allow swiping to switch between pages
//        return false;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        // Never allow swiping to switch between pages
//        return false;
//    }

}

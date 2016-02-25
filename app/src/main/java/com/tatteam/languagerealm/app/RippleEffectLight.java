package com.tatteam.languagerealm.app;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;


import tatteam.com.app_common.R;
import tatteam.com.app_common.ui.drawable.RippleView;

/**
 * Created by ThanhNH on 10/12/2015.
 */
public class RippleEffectLight extends RippleView {
    public RippleEffectLight(Context context) {
        super(context);
    }

    public RippleEffectLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRippleEffect();
    }

    public RippleEffectLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setRippleEffect();
    }

    private void setRippleEffect() {
        setRippleColor(R.color.white);
        setRippleAlpha(90);
        setRippleDuration(80);
//        setRippleType(RippleType.DOUBLE);
//        setZooming(true);
//        setZoomScale(1.5f);
    }
}

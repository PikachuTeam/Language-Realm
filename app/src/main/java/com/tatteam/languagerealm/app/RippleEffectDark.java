package com.tatteam.languagerealm.app;

import android.content.Context;
import android.util.AttributeSet;


import tatteam.com.app_common.R;
import tatteam.com.app_common.ui.drawable.RippleView;

/**
 * Created by ThanhNH on 10/12/2015.
 */
public class RippleEffectDark extends RippleView {
    public RippleEffectDark(Context context) {
        super(context);
    }

    public RippleEffectDark(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRippleEffect();
    }

    public RippleEffectDark(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setRippleEffect();
    }

    private void setRippleEffect() {
        setRippleColor(R.color.dialog_text_color);
        setRippleAlpha(90);
        setRippleDuration(90);
//        setRippleType(RippleType.DOUBLE);
//        setZooming(true);
//        setZoomScale(1.5f);
    }
}

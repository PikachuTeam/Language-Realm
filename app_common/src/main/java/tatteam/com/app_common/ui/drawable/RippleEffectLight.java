package tatteam.com.app_common.ui.drawable;

import android.content.Context;
import android.util.AttributeSet;

import com.andexert.library.RippleView;

import tatteam.com.app_common.R;

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
        setRippleColor(R.color.drawable_ripple_effect_light);
        setRippleAlpha(150);
        setRippleDuration(80);
//        setRippleType(RippleType.DOUBLE);
//        setZooming(true);
//        setZoomScale(1.5f);
    }
}

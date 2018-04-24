package com.damao.tongxunlu.view.refreshlayout;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.damao.tongxunlu.R;
import com.damao.tongxunlu.util.Util;


/**
 * 下拉刷新的图标
 */
public class WCLayout extends FrameLayout implements MaterialHeadListener {

    private Animation hyperspaceJumpAnimation;
    private ImageView progress;
    private ImageView logo;

    public WCLayout(Context context) {
        this(context, null);
    }

    public WCLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WCLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        Context context = getContext();

        LayoutParams layoutParams = new LayoutParams((int) getResources().getDimension(R.dimen.default_60), (int) getResources().getDimension(R.dimen.default_60));
        layoutParams.gravity = Gravity.CENTER;

        logo = new ImageView(context);
        logo.setLayoutParams(layoutParams);
        logo.setImageResource(R.drawable.load_icon);
        addView(logo);

        progress = new ImageView(context);
        progress.setLayoutParams(layoutParams);
        progress.setImageResource(R.drawable.load_progress_icon);
        addView(progress);

        startSunLineAnim();
    }


    /**
     * 开启转圈圈
     */
    public void startSunLineAnim() {
        if (hyperspaceJumpAnimation == null) {
            hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                    getContext(), R.anim.loading_animation);

        }
        progress.startAnimation(hyperspaceJumpAnimation);
    }

    /**
     * 停止动画
     */
    public void cancelSunLineAnim() {
        progress.clearAnimation();
    }

    @Override
    public void onComlete(MaterialRefreshLayout materialRefreshLayout) {
        cancelSunLineAnim();
        ViewCompat.setScaleX(this, 0);
        ViewCompat.setScaleY(this, 0);
    }

    @Override
    public void onBegin(MaterialRefreshLayout materialRefreshLayout) {
        ViewCompat.setScaleX(this, 0.001f);
        ViewCompat.setScaleY(this, 0.001f);
    }

    @Override
    public void onPull(MaterialRefreshLayout materialRefreshLayout, float fraction) {
        float a = Util.limitValue(1, fraction);

        ViewCompat.setScaleX(progress, a);
        ViewCompat.setScaleY(progress, a);
        ViewCompat.setAlpha(progress, a);
        ViewCompat.setScaleX(logo, a);
        ViewCompat.setScaleY(logo, a);
        ViewCompat.setAlpha(logo, a);
        ViewCompat.setScaleX(this, a);
        ViewCompat.setScaleY(this, a);
        ViewCompat.setAlpha(this, a);
    }

    @Override
    public void onRelease(MaterialRefreshLayout materialRefreshLayout, float fraction) {
    }

    @Override
    public void onRefreshing(MaterialRefreshLayout materialRefreshLayout) {
        startSunLineAnim();
    }
}

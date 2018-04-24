package com.damao.tongxunlu;

import android.os.Bundle;

import com.damao.tongxunlu.view.SlidingLayout;


/**
 * 右滑退出
 */
public abstract class SlidingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (enableSliding()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
    }

    protected boolean enableSliding() {
        return true;
    }
}

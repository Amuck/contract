package com.damao.tongxunlu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.widget.ProgressBar;

import com.damao.tongxunlu.R;
import com.hjhrq1991.library.tbs.TbsBridgeWebView;

/**
 * Created by chenlong on 2017/7/11.
 *
 * 带进度条的WebView
 */

@SuppressWarnings("deprecation")
public class ProgressWebView extends TbsBridgeWebView {

    private ProgressBar progressbar;
    private boolean isShowProgress = true;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.YearBill, 0, 0);
        isShowProgress = a.getBoolean(R.styleable.YearBill_progress_visible, true);
        a.recycle();

        progressbar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                10));

        Drawable drawable = context.getResources().getDrawable(R.drawable.progress_bar_states);
        progressbar.setProgressDrawable(drawable);
        addView(progressbar);

        if (!isShowProgress)
            progressbar.setVisibility(GONE);

        setWebChromeClient(new WebChromeClient());
        //是否可以缩放
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(true);
        getSettings().setDefaultZoom(com.tencent.smtt.sdk.WebSettings.ZoomDensity.FAR);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setPluginState(com.tencent.smtt.sdk.WebSettings.PluginState.ON);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setDefaultTextEncodingName("utf-8");
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
            getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }

    public class WebChromeClient extends com.tencent.smtt.sdk.WebChromeClient {
        @Override
        public void onProgressChanged(com.tencent.smtt.sdk.WebView webView, int i) {
            if (i == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE && isShowProgress)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(i);
            }
            super.onProgressChanged(webView, i);
        }

//        @Override
//        public void onProgressChanged(WebView view, int newProgress) {
//            if (newProgress == 100) {
//                progressbar.setVisibility(GONE);
//            } else {
//                if (progressbar.getVisibility() == GONE)
//                    progressbar.setVisibility(VISIBLE);
//                progressbar.setProgress(newProgress);
//            }
//            super.onProgressChanged(view, newProgress);
//        }

    }

//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 10);
//        lp.setMargins(0,t,0,0);
////        lp.leftMargin = t;
////        lp.x = l;
////        lp.y = t;
//        progressbar.setLayoutParams(lp);
//        super.onScrollChanged(l, t, oldl, oldt);
//    }
}

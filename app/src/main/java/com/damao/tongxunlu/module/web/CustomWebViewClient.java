package com.damao.tongxunlu.module.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

import com.damao.tongxunlu.R;
import com.damao.tongxunlu.util.Util;


/**
 * Created by chenlong on 2018/1/25.
 * 
 */

public class CustomWebViewClient extends com.tencent.smtt.sdk.WebViewClient {
    public interface onPageStartedLisetner {
        void onPageStartedSuccess();
        void onPageStartedFail();
    }
    private Context context;
    private String successUrl;
    private String title;
    /**
     * 需要拦截的失败界面
     */
    private String failUrl = "";
    /**
     * 成功后打开的界面
     */
    private String openActivity = "";
    private onPageStartedLisetner onPageStartedLisetner = null;

    public CustomWebViewClient(Context context, String successUrl, String title,
                               String failUrl, String openActivity,
                               CustomWebViewClient.onPageStartedLisetner onPageStartedLisetner) {
        this.context = context;
        this.successUrl = successUrl;
        this.title = title;
        this.failUrl = failUrl;
        this.openActivity = openActivity;
        this.onPageStartedLisetner = onPageStartedLisetner;
    }

//    @Override
//    public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String url) {
//        // 统一拦截
//        return false;
//    }

    @Override
    public void onPageStarted(com.tencent.smtt.sdk.WebView webView, String url, Bitmap bitmap) {
        if (!TextUtils.isEmpty(successUrl) && url.equals(successUrl)) {
            if (TextUtils.isEmpty(openActivity)) {
                Util.showToast(context.getString(R.string.success_msg));
                suceessHandle();
            } else
                openActivity();
            if (null != onPageStartedLisetner)
                onPageStartedLisetner.onPageStartedSuccess();
        }
        if (!TextUtils.isEmpty(failUrl) && url.equals(failUrl)) {
            // 失败
            if (null != onPageStartedLisetner)
                onPageStartedLisetner.onPageStartedFail();
        }
        super.onPageStarted(webView, url, bitmap);
    }

    /**
     * 成功的处理
     */
    private void suceessHandle() {
    }

    /**
     * 打开特定的activity
     */
    private void openActivity() {
//        switch (openActivity) {
//        }
    }

    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
        // super.onReceivedSslError(view, handler, error);
        // 接受所有网站的证书，忽略SSL错误，执行访问网页
        handler.proceed();
    }
}

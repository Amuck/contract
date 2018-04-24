package com.damao.tongxunlu.module.yellow;

import android.os.Bundle;
import android.text.TextUtils;

import com.damao.tongxunlu.BaseLazyFragment;
import com.damao.tongxunlu.R;
import com.damao.tongxunlu.module.web.CustomWebViewClient;
import com.damao.tongxunlu.view.ProgressWebView;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;

import butterknife.BindView;

/**
 * Created by chenlong on 2018/2/1.
 * 黄页
 */

public class YellowFragment extends BaseLazyFragment {

    @BindView(R.id.web)
    ProgressWebView web;

    private String url;
    private String title = "";
    /**
     * post参数
     */
    private String params = "";
    /**
     * 需要拦截的成功界面
     */
    private String successUrl = "";
    /**
     * 需要拦截的失败界面
     */
    private String failUrl = "";
    /**
     * 成功后打开的界面
     */
    private String openActivity = "";
    /**
     * 失败后打开的界面
     */
    private String openActivityFail = "";
    private CustomWebViewClient.onPageStartedLisetner onPageStartedLisetner = new CustomWebViewClient.onPageStartedLisetner() {
        @Override
        public void onPageStartedSuccess() {
        }

        @Override
        public void onPageStartedFail() {
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_yellow;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initWeb();
        getData();
    }

    private void initWeb() {
        CustomWebViewClient customWebViewClient = new CustomWebViewClient(getContext(), successUrl, title, failUrl,
                openActivity, onPageStartedLisetner);
        web.setWebViewClient(customWebViewClient);
    }

    private void getData() {
        if (!TextUtils.isEmpty(url)) {
            web.loadUrl(url);
        }
    }

    public void clearWebViewCache() {
        // 清除cookie即可彻底清除缓存
        CookieSyncManager.createInstance(getContext());
        CookieManager.getInstance().removeAllCookie();
    }

    private void postData() {
        if (!TextUtils.isEmpty(url)) {
            web.postUrl(url, params.getBytes());
        }
    }
}

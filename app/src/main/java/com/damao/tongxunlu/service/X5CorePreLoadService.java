package com.damao.tongxunlu.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.damao.tongxunlu.BaseApplication;
import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by chenlong on 2017/12/27.
 * x5预加载
 */

public class X5CorePreLoadService extends IntentService {
    private static final String TAG = X5CorePreLoadService.class.getSimpleName();

    public X5CorePreLoadService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //在这里添加我们要执行的代码，Intent中可以保存我们所需的数据，
        //每一次通过Intent发送的命令将被顺序执行
        initX5();
    }

    /**
     * 初始化X5内核
     */
    private void initX5() {
        if (!QbSdk.isTbsCoreInited()) {
            QbSdk.preInit(getApplicationContext(), null);// 设置X5初始化完成的回调接口
        }

        QbSdk.initX5Environment(BaseApplication.getAppContext(), QbSdk.WebviewInitType.FIRSTUSE_AND_PRELOAD, cb);
    }

    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

        @Override
        public void onViewInitFinished(boolean arg0) {
            //初始化完成回调
        }

        @Override
        public void onCoreInitFinished() {
        }
    };

}

package com.damao.tongxunlu;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.damao.tongxunlu.util.Util;
import com.damao.tongxunlu.view.AppProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by chenlong on 2017/4/24.
 * <p>
 * activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected CompositeDisposable disposables = new CompositeDisposable();
    private Unbinder bind;
    protected View view;
    protected AppProgressDialog appProgressDialog;

    /**
     * 错误提示
     */
    protected View errorView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setFlags();
        super.onCreate(savedInstanceState);

        // 清空被回收之前的dispose
        if (disposables.size() > 0) {
            disposables.dispose();
        }

        //设置布局内容
        view = LayoutInflater.from(this).inflate(getLayoutId(), null);
        setContentView(view);

        appProgressDialog = new AppProgressDialog(this, R.style.CustomProgressDialog);
        appProgressDialog.setCancelable(false);
        appProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        //初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        // 初始化友盟
//        try {
//            mPushAgent = PushAgent.getInstance(this);
//            mPushAgent.onAppStart();
//        } catch (Exception e) {
//        }

        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();

        Window window = getWindow();
        setHideVirtualKey(window);
        window.getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> setHideVirtualKey(window));
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    protected void setFlags() {

    }

    /**
     * 刷新错误界面
     */
    protected void refreshError() {

    }
    /**
     * 移除错误提示
     *
     * @param parentView        错误信息加入的父容器
     * @param dataView              数据列表
     */
    public void removeError(ViewGroup parentView, View dataView) {
        dataView.setVisibility(View.VISIBLE);
        if (null != errorView) {
            parentView.removeView(errorView);
            errorView = null;
        }
    }

    protected void toast(String msg) {
        if (!TextUtils.isEmpty(msg))
            Util.showToast(msg);
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initToolBar();


    public void loadData() {
    }


    public void showProgressBar() {
        appProgressDialog.show();
    }


    public void hideProgressBar() {
        appProgressDialog.dismiss();
//        appProgressDialog.hide();
    }
    public void showProgressBarHideKeyboard() {
        Util.hideKeyBoard(this);
        appProgressDialog.show();
    }


    public void hideProgressBarHideKeyboard() {
        Util.hideKeyBoard(this);
        appProgressDialog.dismiss();
    }


    public void initRecyclerView() {
    }


    public void initRefreshLayout() {
    }


    public void finishTask() {
    }

    @Override
    protected void onDestroy() {
        if (disposables.size() > 0) {
            disposables.dispose();
        }
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public void finish() {
        Util.hideKeyBoard(this);
        super.finish();
    }

    public void setHideVirtualKey(Window window){
        //保持布局状态
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                //隐藏导航栏
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        if (Build.VERSION.SDK_INT>=19){
            uiOptions |= 0x00001000;
        }else{
            uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        window.getDecorView().setSystemUiVisibility(uiOptions);
    }
    /**
     * 隐藏虚拟按键，并且全屏
     */
//    protected void hideBottomUIMenu() {
//        //隐藏虚拟按键，并且全屏
//        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
//            View v = this.getWindow().getDecorView();
//            v.setSystemUiVisibility(View.GONE);
//        } else if (Build.VERSION.SDK_INT >= 19) {
//            //for new api versions.
//            View decorView = getWindow().getDecorView();
//            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//            decorView.setSystemUiVisibility(uiOptions);
//        }
//    }
}

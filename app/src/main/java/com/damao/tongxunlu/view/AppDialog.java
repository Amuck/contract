package com.damao.tongxunlu.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.damao.tongxunlu.R;
import com.damao.tongxunlu.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chenlong on 2017/6/15.
 * <p>
 * dialog
 */

public class AppDialog extends Dialog {
    public interface AppDialogOkClickListener {
        void onOkClick();
    }
    public interface AppDialogCancelClickListener {
        void onCancelClick();
    }

    @BindView(R.id.dialog_img)
    ImageView dialogImg;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.ok)
    TextView ok;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.positi)
    TextView positi;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.d2)
    View divider;

    private Context context;
    private Unbinder unbinder;

    private AppDialogOkClickListener okListener = null;
    private AppDialogCancelClickListener cancelClickListener = null;

    private Window window;

    public AppDialog(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public AppDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    public AppDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    private void initView(@NonNull Context context) {
        setContentView(R.layout.layout_dialog);

        this.context = context;
        unbinder = ButterKnife.bind(this);

        window = getWindow();
        assert window != null : "AppDialog Window cannot be null!";
        setDialogSize(context.getResources().getDimensionPixelSize(
                R.dimen.app_dialog_top_noimg));
        setHideVirtualKey(window);
        window.getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> setHideVirtualKey(window));
    }

    /**
     * 改变dialog的显示的位置和大小
     */
    public void setDialogSize(int top) {
        WindowManager.LayoutParams lp = window.getAttributes();
//        dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        //显示的坐标
//        lp.gravity = Gravity.CENTER;
//        lp.x = 150;
//        lp.y = 50;
        lp.y = top;
        //dialog的大小
//        int width = getResources().getDimensionPixelOffset(R.dimen.d_width);
//        int height = getResources().getDimensionPixelOffset(R.dimen.d_height);
//        lp.width = width;
//        lp.height = height;
        window.setAttributes(lp);
    }

    /**
     * 隐藏虚拟按键
     * @param window
     */
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

    public void setDialogImg(int resId) {
        setDialogSize(context.getResources().getDimensionPixelSize(
                R.dimen.app_dialog_top));
        dialogImg.setVisibility(View.VISIBLE);
        dialogImg.setImageResource(resId);
    }

    public void setTitleVisible(int visible) {
        title.setVisibility(visible);
    }

    public void setTitleTxt(String txt) {
        title.setText(txt);
    }

    public void setTitleTxt(int rsId) {
        title.setText(rsId);
    }

    public void changeImgStatus(int visible) {
        dialogImg.setVisibility(visible);
    }

    public void setContentGravity(int gravity) {
        content.setGravity(gravity);
    }

    public void setContent(String text ) {
        content.setText(text);
    }

    public void setContent(SpannableString spannableString) {
        content.setText(spannableString);
    }

    public void setContentVisible(int visible) {
        content.setVisibility(visible);
    }

    public void setContentSize(float size ) {
        content.setTextSize(size);
    }

    public void setOkTxt(String txt) {
        ok.setText(txt);
        positi.setText(txt);
    }
    public void setOkTxtColor(int colorId) {
        ok.setTextColor(Util.getColor(colorId));
        positi.setTextColor(Util.getColor(colorId));
    }

    public void setCancelTxt(String txt) {
        cancel.setText(txt);
    }

    public void setCancelTxtColor(int colorId) {
        cancel.setTextColor(Util.getColor(colorId));
    }

    public void setOkListener(AppDialogOkClickListener okListener) {
        this.okListener = okListener;
    }

    public void setCancelClickListener(AppDialogCancelClickListener cancelClickListener) {
        this.cancelClickListener = cancelClickListener;
    }

    /**
     * 只有一个按钮, 使用 {@link AppDialogOkClickListener}进行回调
     */
    public void setOneButton() {
        positi.setVisibility(View.VISIBLE);
        divider.setVisibility(View.GONE);
        ok.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
    }

    public void setNoButton() {
        positi.setVisibility(View.GONE);
        divider.setVisibility(View.GONE);
        ok.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        unbinder.unbind();
    }

    @OnClick({R.id.cancel, R.id.ok, R.id.positi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                if (null != cancelClickListener)
                    cancelClickListener.onCancelClick();
                dismiss();
                break;
            case R.id.positi:
            case R.id.ok:
                if (null != okListener)
                    okListener.onOkClick();
                dismiss();
                break;
        }
    }
}

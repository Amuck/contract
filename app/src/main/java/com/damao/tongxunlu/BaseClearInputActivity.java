package com.damao.tongxunlu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.damao.tongxunlu.util.CodeUtils;
import com.damao.tongxunlu.util.IdentifyCountUtil;
import com.damao.tongxunlu.util.Util;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by chenlong on 2017/4/24.
 *
 * clear input activity基类
 */
public abstract class BaseClearInputActivity extends BaseActivity implements TextWatcher {
    protected CodeUtils codeUtils;
    protected Disposable disposableCoundown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        codeUtils = CodeUtils.getInstance();

        super.onCreate(savedInstanceState);
    }

    /**
     * change the clear button state
     * @param imageButton
     */
    protected void changeClearBtnState(CharSequence s, ImageButton imageButton) {
        if (s.length() > 0) {
            // 如果有内容时候 显示删除按钮
            imageButton.setVisibility(View.VISIBLE);
        } else {
            imageButton.setVisibility(View.GONE);
        }
    }

    /**
     * clean the content of edittext
     * @param editText
     * @param imageButton
     */
    protected void clearInput(EditText editText, ImageButton imageButton) {
        getFocus(editText);
        editText.setText("");
        imageButton.setVisibility(View.INVISIBLE);
    }

    /**
     * change the pwd view state
     */
    protected void hideOrShowPwd(ImageButton tempBtn, EditText tempET , boolean state) {
        getFocus(tempET);

        if (state) {
            tempBtn.setImageResource(R.drawable.display_icon);
            tempET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            tempBtn.setImageResource(R.drawable.hide_icon);
            tempET.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

        // set the selection at the end of pwd view
        CharSequence charSequence = tempET.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    /**
     * get the focus on this edittext view
     * @param editText
     */
    protected void getFocus(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /**
     * 判断输入的图形验证码是否正确
     * @return
     */
    public boolean judgeIdentify(String codeStr) {
        if (TextUtils.isEmpty(codeStr)) {
            toast(getString(R.string.img_identity_code_pls));
            return false;
        }
        String code = codeUtils.getCode();
        if (code.equalsIgnoreCase(codeStr)) {
            return true;
        } else {
            toast(getString(R.string.identity_code_error));
            return false;
        }
    }

    /**
     * init the phone identity state
     * @param viewIdentifyPhoneCode
     */
    protected void initPhoneIdentifyState(TextView viewIdentifyPhoneCode) {
        boolean canCountdown = IdentifyCountUtil.canCountdown();
        if (!canCountdown) {
            int countTime = IdentifyCountUtil.getNowTime();
            viewIdentifyPhoneCode.setText(String.format(getString(R.string.send_again), countTime));
            viewIdentifyPhoneCode.setTextColor(Color.GRAY);
            createCountObserver(viewIdentifyPhoneCode, countTime);
        }
    }

    /**
     * change the phone identity state
     */
    protected void changePhoneIdentifyState(TextView viewIdentifyPhoneCode) {
        boolean state = canCountDown(viewIdentifyPhoneCode);
        if (state) {
            int countTime = 59;
            IdentifyCountUtil.startCount();
            createCountObserver(viewIdentifyPhoneCode, countTime);
        }
    }

    /**
     * create the observerable
     * @param viewIdentifyPhoneCode
     * @param countTime
     */
    private void createCountObserver(TextView viewIdentifyPhoneCode, int countTime) {
        Disposable disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(aLong -> countTime - aLong.intValue())
                .take(countTime + 1)
                .subscribe(integer -> {
                            if (viewIdentifyPhoneCode != null) {
                                viewIdentifyPhoneCode.setTextColor(Color.GRAY);
                                viewIdentifyPhoneCode.setText(String.format(getString(R.string.send_again), integer));
                            }
                        },
                        throwable -> {
                            if (viewIdentifyPhoneCode != null) {
                                viewIdentifyPhoneCode.setTextColor(Util.getColor(R.color.app_red));
                                viewIdentifyPhoneCode.setText(getString(R.string.get_identity_code));
                            }
                        },
                        () -> {
                            if (viewIdentifyPhoneCode != null) {
                                viewIdentifyPhoneCode.setTextColor(Util.getColor(R.color.app_red));
                                viewIdentifyPhoneCode.setText(getString(R.string.get_identity_code));
                            }
                        });
        disposables.add(disposable);
    }

    /**
     * @param viewIdentifyPhoneCode
     * @return      true if can count down
     */
    protected boolean canCountDown(TextView viewIdentifyPhoneCode) {
        boolean state = getString(R.string.get_identity_code).equals(viewIdentifyPhoneCode.getText().toString());
        return state;
    }
    protected boolean canCountDown(Button viewIdentifyPhoneCode) {
        boolean state = getString(R.string.get_identity_code).equals(viewIdentifyPhoneCode.getText().toString());
        return state;
    }

    /**
     * startCountdown
     * @param viewIdentifyPhoneCode
     */
    protected void startCountdown(@NonNull TextView viewIdentifyPhoneCode) {
        Util.checkNotNull(viewIdentifyPhoneCode, "viewIdentifyPhoneCode cannot be null!");
        if (!canCountDown(viewIdentifyPhoneCode))
            return;

        int countTime = 59;
        disposableCoundown = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(aLong -> countTime - aLong.intValue())
                .take(countTime + 1)
                .subscribe(integer -> {
                            if (viewIdentifyPhoneCode != null) {
                                viewIdentifyPhoneCode.setTextColor(Color.GRAY);
                                viewIdentifyPhoneCode.setText(String.format(getString(R.string.send_again), integer));
                            }
                        },
                        throwable -> {
                            if (viewIdentifyPhoneCode != null) {
                                viewIdentifyPhoneCode.setTextColor(Util.getColor(R.color.app_red));
                                viewIdentifyPhoneCode.setText(getString(R.string.get_identity_code));
                            }
                        },
                        () -> {
                            if (viewIdentifyPhoneCode != null) {
                                viewIdentifyPhoneCode.setTextColor(Util.getColor(R.color.app_red));
                                viewIdentifyPhoneCode.setText(getString(R.string.get_identity_code));
                            }
                        });
        disposables.add(disposableCoundown);
    }
    /**
     * startCountdown
     * @param viewIdentifyPhoneCode
     */
    protected void startCountdown(@NonNull Button viewIdentifyPhoneCode) {
        Util.checkNotNull(viewIdentifyPhoneCode, "viewIdentifyPhoneCode cannot be null!");
        if (!canCountDown(viewIdentifyPhoneCode))
            return;

        int countTime = 59;
        disposableCoundown = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(aLong -> countTime - aLong.intValue())
                .take(countTime + 1)
                .subscribe(integer -> {
                            if (viewIdentifyPhoneCode != null) {
                                viewIdentifyPhoneCode.setEnabled(false);
                                viewIdentifyPhoneCode.setText(String.format(getString(R.string.send_again), integer));
                            }
                        },
                        throwable -> {
                            if (viewIdentifyPhoneCode != null) {
                                viewIdentifyPhoneCode.setEnabled(true);
                                viewIdentifyPhoneCode.setText(getString(R.string.get_identity_code));
                            }
                        },
                        () -> {
                            if (viewIdentifyPhoneCode != null) {
                                viewIdentifyPhoneCode.setEnabled(true);
                                viewIdentifyPhoneCode.setText(getString(R.string.get_identity_code));
                            }
                        });
        disposables.add(disposableCoundown);
    }
}

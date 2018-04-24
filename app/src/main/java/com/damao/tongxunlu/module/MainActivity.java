package com.damao.tongxunlu.module;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.damao.tongxunlu.BaseActivity;
import com.damao.tongxunlu.R;
import com.damao.tongxunlu.module.people.PeopleFragment;
import com.damao.tongxunlu.module.phone.PhoneFragment;
import com.damao.tongxunlu.module.yellow.YellowFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenlong on 2018/2/1.
 *
 */

public class MainActivity extends BaseActivity {
    private static final int HOME_POSITON = R.id.call;
    private static final int PRODUCT_POSITON = R.id.people;
    private static final int USER_POSITON = R.id.yellow;
    // permission
    private static final int SDK_PERMISSION_REQUEST = 127;
    private static final int UPDATE_PERMISSION_REQUEST = 128;

    @BindView(R.id.call)
    RadioButton call;
    @BindView(R.id.people)
    RadioButton people;
    @BindView(R.id.yellow)
    RadioButton wap;
    @BindView(R.id.filter_group)
    RadioGroup filterGroup;
    @BindView(R.id.main_container)
    FrameLayout mainContainer;

    private FragmentManager fm;
    private Fragment homeFragment;
    private Fragment productFragment;
    private Fragment userFragment;
    /**
     * the selected fragment
     */
    private Fragment currentFrag;
    /**
     * the selected textview
     */
    private int currentId = HOME_POSITON;
    /**
     * judge the backpress if exit
     */
    private Long firstTime = 0L;
    @SuppressWarnings("unused")
    private String permissionInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        homeFragment = new PhoneFragment();
        productFragment = new PeopleFragment();
        userFragment = new YellowFragment();
        switchContent(homeFragment);
        filterGroup.check(HOME_POSITON);

        // 获取权限
        getPersimmons();
    }

    @Override
    public void initToolBar() {

    }

    /**
     * switch the fragment
     *
     * @param position
     */
    public void swicthFragment(int position) {
        switch (position) {
            case HOME_POSITON:
                switchContent(homeFragment);
                break;
            case PRODUCT_POSITON:
                switchContent(productFragment);
                break;
            case USER_POSITON:
                switchContent(userFragment);
                break;
            default:
                break;
        }
    }

    /**
     * 动态添加fragment，不会重复创建fragment
     *
     * @param to 将要加载的fragment
     */
    public void switchContent(Fragment to) {
        if (currentFrag != to) {
            if (!to.isAdded()) {// 如果to fragment没有被add则增加一个fragment
                if (currentFrag != null) {
                    fm.beginTransaction().hide(currentFrag).commitAllowingStateLoss();
                }
                fm.beginTransaction()
                        .add(R.id.main_container, to)
                        .commitAllowingStateLoss();
            } else {
                fm.beginTransaction().hide(currentFrag).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
            currentFrag = to;
        }
    }

    @OnClick({R.id.call, R.id.people, R.id.yellow})
    public void onViewClicked(View view) {
        if (view.getId() == currentId)
            return;

        switch (view.getId()) {
            case R.id.call:
                // 拨号
                onNavigateClick(HOME_POSITON);
                break;
            case R.id.people:
                // 联系人
                onNavigateClick(PRODUCT_POSITON);
                break;
            case R.id.yellow:
                // 黄页
                onNavigateClick(USER_POSITON);
                break;
        }
    }

    private void onNavigateClick(int position) {
        filterGroup.check(position);
            swicthFragment(position);
            currentId = position;
    }

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 1500) {
            toast(getString(R.string.back_again_exit));
            firstTime = secondTime;
        } else {
            finish();
        }
    }

    @TargetApi(23)
    private void getPersimmons() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<>();
            // 定位精确位置,定位权限为必须权限，用户如果禁止，则每次进入都会申请
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }
}

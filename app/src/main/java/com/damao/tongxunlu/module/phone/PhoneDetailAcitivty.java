package com.damao.tongxunlu.module.phone;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.damao.tongxunlu.BaseActivity;
import com.damao.tongxunlu.R;
import com.damao.tongxunlu.adapter.PhoneDetailAdapter;
import com.damao.tongxunlu.adapter.PhoneListAdapter;
import com.damao.tongxunlu.entity.CallEntity;
import com.damao.tongxunlu.entity.CallLogNeedAllBean;
import com.damao.tongxunlu.module.add.AddPeopleActivity;
import com.damao.tongxunlu.module.select.SelectPeopleActivity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenlong on 2018/3/15.
 * 通话记录详情
 */

public class PhoneDetailAcitivty extends BaseActivity {
    public static final String PHONE_DETAIL_KEY = "phone_detail_key";
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    public static final String PEOPLE_DETAIL_KEY = "people_detail_key";

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.all_log)
    TextView all_log;
    @BindView(R.id.top_log)
    RecyclerView topLog;
    @BindView(R.id.call_list)
    RecyclerView call_list;
    @BindView(R.id.new_people)
    TextView newPeople;
    @BindView(R.id.d5)
    View d5;
    @BindView(R.id.save_people)
    TextView savePeople;
    @BindView(R.id.d6)
    View d6;
    @BindView(R.id.d3)
    View d3;
    @BindView(R.id.stop_people)
    TextView stopPeople;
    @BindView(R.id.iv_more_txt)
    TextView iv_more_txt;

    private CallLogNeedAllBean callLogNeedAllBean = null;
    private ArrayList<CallEntity> callEntities = new ArrayList<>();
    /**
     * 是否是查看联系人，默认不是
     */
    private boolean isDetil = false;
    private String phoneNum;

    @Override
    public int getLayoutId() {
        return R.layout.activity_phone_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        isDetil = getIntent().getBooleanExtra(PEOPLE_DETAIL_KEY, false);
        callLogNeedAllBean = (CallLogNeedAllBean) getIntent().getSerializableExtra(PHONE_DETAIL_KEY);
        if (null == callLogNeedAllBean) {
            toast(getString(R.string.phone_detail_error));
            finish();
        }

        callEntities = callLogNeedAllBean.getCallEntities();

        setUpUi();
        initPhoneRecycle();
        if (!isDetil)
            initRecycle();
        else {
            all_log.setVisibility(View.GONE);
            d3.setVisibility(View.GONE);
            topLog.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
        }
    }

    private void setUpUi() {
        String remarkName = callLogNeedAllBean.getRemarkName();
        if (TextUtils.isEmpty(remarkName)) {
            // 陌生人
            name.setText(callLogNeedAllBean.getNumber());
            submit.setText("");
            newPeople.setVisibility(View.VISIBLE);
            d5.setVisibility(View.VISIBLE);
            savePeople.setVisibility(View.VISIBLE);
            d6.setVisibility(View.VISIBLE);
        } else {
            name.setText(remarkName);
            submit.setText(callLogNeedAllBean.getNumber());
            newPeople.setVisibility(View.GONE);
            d5.setVisibility(View.GONE);
            savePeople.setVisibility(View.GONE);
            d6.setVisibility(View.GONE);
        }
        if (callEntities.size() > 5) {
            all_log.setVisibility(View.VISIBLE);
            d3.setVisibility(View.VISIBLE);
        } else {
            all_log.setVisibility(View.GONE);
            d3.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化通话记录列表
     */
    private void initRecycle() {
        PhoneDetailAdapter phoneDetailAdapter;
        if (callEntities.size() > 5)
            phoneDetailAdapter = new PhoneDetailAdapter(this, callEntities.subList(0, 5));
        else
            phoneDetailAdapter = new PhoneDetailAdapter(this, callEntities);
        topLog.setAdapter(phoneDetailAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        topLog.setLayoutManager(manager);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        topLog.setItemAnimator(itemAnimator);
        topLog.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化手机号码列表
     */
    private void initPhoneRecycle() {
        PhoneListAdapter phoneListAdapter;
        phoneListAdapter = new PhoneListAdapter(this, getAllPhone(callLogNeedAllBean.getRemarkName()), new PhoneListAdapter.MyItemClickListener() {
            @Override
            public void onCallClick(String phone, int position) {
                // 打电话
                call(phone);
            }

            @Override
            public void onMsgClick(String phone, int position) {
                // 發短信
                doSendSMSTo(phone);
            }
        });
        call_list.setAdapter(phoneListAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        call_list.setLayoutManager(manager);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        call_list.setItemAnimator(itemAnimator);
    }

    /**
     * 获取该用户的所有手机号
     * @param name
     * @return
     */
    private List<String> getAllPhone(String name) {
        ArrayList<String> phoneNumbers = new ArrayList<>();
        if (TextUtils.isEmpty(name)) {
            phoneNumbers.add(callLogNeedAllBean.getNumber());
            return phoneNumbers;
        }
        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
//取得电话本中开始一项的光标
// Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//根据传入的姓名查询通讯录，这个姓名就是上一个activity传过来的参数
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.PhoneLookup.DISPLAY_NAME + "=?",
                new String[]{name}, null);
        if (null != cursor && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                //获取联系人的ID
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                //查询电话类型的数据操作
                Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null, null);
                if (phones != null) {
                    while (phones.moveToNext()) {
                        String phoneNumber = phones.getString(phones.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //添加Phone的信息
                        phoneNumbers.add(phoneNumber.trim());
                    }
                    phones.close();

                    // 去重
                    Set<String> set = new LinkedHashSet<>();
                    set.addAll(phoneNumbers);
                    phoneNumbers.clear();
                    phoneNumbers.addAll(set);
                }
            }
            cursor.close();
        }
        return phoneNumbers;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(getString(R.string.phone_detail_title));
        if (isDetil) {
            iv_more_txt.setVisibility(View.VISIBLE);
            iv_more_txt.setText(getString(R.string.edit));
        } else {
            iv_more_txt.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.iv_back_click, R.id.all_log, R.id.new_people, R.id.save_people,
            R.id.stop_people, R.id.iv_more_txt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_more_txt:
                // 编辑
                Intent intent3 = new Intent(PhoneDetailAcitivty.this, AddPeopleActivity.class);
                intent3.putExtra(AddPeopleActivity.EDIT_KEY, callLogNeedAllBean);
                startActivity(intent3);
                finish();
                break;
            case R.id.iv_back_click:
                finish();
                break;
            case R.id.all_log:
                // 查看全部通话记录
                Intent intent = new Intent(PhoneDetailAcitivty.this, PhoneDetailListAcitivty.class);
                intent.putExtra(PhoneDetailAcitivty.PHONE_DETAIL_KEY, callLogNeedAllBean);
                startActivity(intent);
                break;
            case R.id.new_people:
                // 新建联系人
                Intent intent1 = new Intent(PhoneDetailAcitivty.this, AddPeopleActivity.class);
                intent1.putExtra(AddPeopleActivity.PHONE_KEY, callLogNeedAllBean.getNumber());
                startActivity(intent1);
                break;
            case R.id.save_people:
                // 保存到已有联系人
                Intent intent2 = new Intent(PhoneDetailAcitivty.this, SelectPeopleActivity.class);
                intent2.putExtra(AddPeopleActivity.PHONE_KEY, callLogNeedAllBean.getNumber());
                startActivity(intent2);
                finish();
                break;
            case R.id.stop_people:
                // 阻止此号码
                break;
        }
    }

    /**
     * 调起系统发短信功能
     */
    public void doSendSMSTo(String phone) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(callLogNeedAllBean.getNumber())) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
            startActivity(intent);
        }
    }

    /**
     * 打电话
     */
    private void call(String phone) {
        // 检查是否获得了权限（Android6.0运行时权限）
        if (ContextCompat.checkSelfPermission(PhoneDetailAcitivty.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // 没有获得授权，申请授权
            phoneNum = phone;
            if (ActivityCompat.shouldShowRequestPermissionRationale(PhoneDetailAcitivty.this,
                    Manifest.permission.CALL_PHONE)) {
                // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                // 弹窗需要解释为何需要该权限，再次请求授权
                toast("请授权！");

                // 帮跳转到该应用的设置界面，让用户手动授权
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            } else {
                // 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(PhoneDetailAcitivty.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        } else {
            // 已经获得授权，可以打电话
            Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri
                    .parse("tel:" + phone));
            startActivity(dialIntent);
        }
    }

    // 处理权限申请的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权成功，继续打电话
                    call(phoneNum);
                } else {
                    // 授权失败！
                    toast("授权失败！");
                }
                break;
            }
        }
    }
}

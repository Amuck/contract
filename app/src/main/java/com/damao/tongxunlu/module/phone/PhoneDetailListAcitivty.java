package com.damao.tongxunlu.module.phone;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.damao.tongxunlu.BaseActivity;
import com.damao.tongxunlu.R;
import com.damao.tongxunlu.adapter.PhoneDetailAdapter;
import com.damao.tongxunlu.entity.CallEntity;
import com.damao.tongxunlu.entity.CallLogNeedAllBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenlong on 2018/3/16.
 * 对应手机号的通话记录列表
 */

public class PhoneDetailListAcitivty extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.top_log)
    RecyclerView topLog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_phone_all_log;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        CallLogNeedAllBean callLogNeedAllBean = (CallLogNeedAllBean) getIntent().getSerializableExtra(PhoneDetailAcitivty.PHONE_DETAIL_KEY);
        ArrayList<CallEntity> callEntities = callLogNeedAllBean.getCallEntities();

        PhoneDetailAdapter phoneDetailAdapter;
        phoneDetailAdapter = new PhoneDetailAdapter(this, callEntities);
        topLog.setAdapter(phoneDetailAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        topLog.setLayoutManager(manager);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        topLog.setItemAnimator(itemAnimator);
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(getString(R.string.phone_log_title));
    }

    @OnClick({R.id.iv_back_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_click:
                finish();
                break;
        }
    }
}

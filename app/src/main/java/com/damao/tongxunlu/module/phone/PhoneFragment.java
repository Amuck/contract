package com.damao.tongxunlu.module.phone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.damao.tongxunlu.BaseLazyFragment;
import com.damao.tongxunlu.R;
import com.damao.tongxunlu.adapter.CallAdapter;
import com.damao.tongxunlu.entity.CallLogNeedAllBean;
import com.damao.tongxunlu.util.ConstanceUtil;
import com.damao.tongxunlu.util.Util;
import com.damao.tongxunlu.view.refreshlayout.MaterialRefreshLayout;
import com.damao.tongxunlu.view.refreshlayout.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenlong on 2018/1/12.
 * 拨号
 */

public class PhoneFragment extends BaseLazyFragment implements PhoneContract.View{
    /**
     * 全部
     */
    private static final int ALL = R.id.all;
    /**
     *未接来电
     */
    private static final int NOT = R.id.not;
    /**
     * 常用联系人
     */
    private static final int ALLWAYS = R.id.allways;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.refresh)
    MaterialRefreshLayout materialRefreshLayout;
    @BindView(R.id.call)
    TextView call;
    @BindView(R.id.menu)
    TextView menu;
    @BindView(R.id.filter_group)
    RadioGroup filterGroup;

    /**
     * 禁止上拉下拉同时进行, true:可以进行上拉刷新
     */
    private boolean canLoadMore = false;
    /**
     * 筛选状态
     */
    private int type = ConstanceUtil.CONTRACT_ALL;

    private CallAdapter callAdapter;

    private PhoneContract.Presenter presenter;
    private ArrayList<CallLogNeedAllBean> arrayList;

    private CallAdapter.MyItemClickListener myItemClickListener = new CallAdapter.MyItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            // 调用系统方法拨打电话
            Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri
                    .parse("tel:" + arrayList.get(position).getNumber()));
            startActivity(dialIntent);
        }

        @Override
        public void detailClick(int position) {
            Intent intent = new Intent(getContext(), PhoneDetailAcitivty.class);
            CallLogNeedAllBean callLogNeedAllBean = arrayList.get(position);
            intent.putExtra(PhoneDetailAcitivty.PHONE_DETAIL_KEY, callLogNeedAllBean );
            startActivity(intent);
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_phone;
    }

    @Override
    public void finishCreateView(Bundle state) {
        new PhonePresenter(getActivity(), this);

        initFreshLayout();
        initFileter();
        initRecycle();
    }

    private void initFileter() {
        filterGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case ALL:
                    type = ConstanceUtil.CONTRACT_ALL;
                    break;
                case NOT:
                    type = ConstanceUtil.CONTRACT_NOT;
                    break;
                case ALLWAYS:
                    type = ConstanceUtil.CONTRACT_ALLWAYS;
                    break;
            }
            materialRefreshLayout.startRefresh();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        materialRefreshLayout.startRefresh();
    }

    /**
     * 初始化刷新
     */
    private void initFreshLayout() {
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                canLoadMore = false;
                // 开始刷新数据后禁止切换筛选
                changeFilterState(false);
                presenter.getCallList(type);
            }

            @Override
            public void onfinish() {
            }
        });

        // 初始化自动加载
        materialRefreshLayout.autoRefresh();
    }

    /**
     * 初始化列表
     */
    private void initRecycle() {
        callAdapter = new CallAdapter(getActivity(), myItemClickListener);
        list.setAdapter(callAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(manager);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        list.setItemAnimator(itemAnimator);

//        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if (!canLoadMore) {
//                    canLoadMore = true;
//                    return;
//                }
//
//                int endCompletelyPosition = manager.findLastCompletelyVisibleItemPosition();
//                if (endCompletelyPosition == callAdapter.getItemCount() - 1 && !callAdapter.isLoadingNow()
//                        && callAdapter.getItemCount() > 1) {
//                    //执行加载更多的方法
//                    callAdapter.startLoad();
//                }
//            }
//        });
    }

    /**
     * 改变单选按钮状态
     *
     * @param enable
     */
    private void changeFilterState(boolean enable) {
        call.setEnabled(enable);
        menu.setEnabled(enable);
    }

    @OnClick({ R.id.call, R.id.menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.call:
                // 拨号
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+""));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.menu:
                // 菜单
                break;
        }
    }

    @Override
    public void setPresenter(PhoneContract.Presenter presenter) {
        this.presenter = Util.checkNotNull(presenter);
    }

    @Override
    public void getCallListSuccess(ArrayList<CallLogNeedAllBean> arrayList) {
        this.arrayList = arrayList;
        callAdapter.refreshDate(arrayList);
        materialRefreshLayout.finishRefresh();
        changeFilterState(true);
    }

    @Override
    public void getCallListFailed(String msg) {
        Util.showToast(msg);
        materialRefreshLayout.finishRefresh();
        changeFilterState(true);
    }

    @Override
    public void getCallDetailSuccess(CallLogNeedAllBean callLogNeedAllBean) {
        changeFilterState(true);
    }

    @Override
    public void getCallDetailFailed(String msg) {
        changeFilterState(true);
    }


    @Override
    public void onDestroy() {
        presenter.destory();
        presenter = null;
        super.onDestroy();
    }
}

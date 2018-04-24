package com.damao.tongxunlu.module.people;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.damao.tongxunlu.BaseLazyFragment;
import com.damao.tongxunlu.R;
import com.damao.tongxunlu.adapter.ContactAdapter;
import com.damao.tongxunlu.entity.CallEntity;
import com.damao.tongxunlu.entity.CallLogNeedAllBean;
import com.damao.tongxunlu.entity.FriendEntity;
import com.damao.tongxunlu.entity.PeopleResultEntity;
import com.damao.tongxunlu.module.add.AddPeopleActivity;
import com.damao.tongxunlu.module.phone.PhoneDetailAcitivty;
import com.damao.tongxunlu.module.search.SearchActivity;
import com.damao.tongxunlu.util.Util;
import com.damao.tongxunlu.view.CircleTextView;
import com.damao.tongxunlu.view.DividerItemDecoration;
import com.damao.tongxunlu.view.PinYinSlideView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenlong on 2018/2/1.
 * 联系人
 */

public class PeopleFragment extends BaseLazyFragment implements PeopleContract.View {
    @BindView(R.id.header)
    TextView header;
    @BindView(R.id.recycler)
    RecyclerView contactList;
    @BindView(R.id.pinYinSlideView)
    PinYinSlideView pinYinSlideView;
    @BindView(R.id.circleText)
    CircleTextView circleText;

    private ContactAdapter contactAdapter;
    private List<FriendEntity> friends = new ArrayList<>();
    private LinearLayoutManager manager;
    private boolean move;
    private int mIndex;

    private PeopleContract.Presenter presenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_people;
    }

    @Override
    public void finishCreateView(Bundle state) {
        new PeopelPresenter(getContext(), this);

        pinYinSlideView.setOnShowTextListener(text -> {
            circleText.setText(text);
            if (text != null) {
                if (!text.equals("↑")) {
                    int position = 0;
                    boolean hasPinyin = false;
                    for (int i = 0; i < friends.size(); i++) {
                        FriendEntity friend = friends.get(i);
                        if (friend.getFirstPinyin().equals(text)) {
                            position = i;
                            hasPinyin = true;
                            break;
                        }
                    }
                    if (hasPinyin) {
                        PeopleFragment.this.scrollToPosition(position);
                    }
                } else {
                    PeopleFragment.this.scrollToPosition(0);
                }
            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getInfo();
    }

    private void getInfo() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions
                .request(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
                .subscribe(granted -> {
                    if (granted) {
                        presenter.getFriends();
                    } else {
                        // Oups permission denied
                        Util.showToast(getString(R.string.people_not_granted));
                    }
                });

    }

    private void setUpUi() {
        manager = new LinearLayoutManager(getContext());
        contactList.setLayoutManager(manager);
        contactAdapter = new ContactAdapter(getContext(), friends);
        contactAdapter.setItemClickListener((phone, position) -> {
            // 跳转详情
            CallLogNeedAllBean callLogNeedAllBean = new CallLogNeedAllBean();
            callLogNeedAllBean.setNumber(phone.get(0));
            String name = friends.get(position).getNickName();
            callLogNeedAllBean.setRemarkName(name);
            ArrayList<CallEntity> callEntities = new ArrayList<>();
            for (int i= 0; i < phone.size(); i++) {
                CallEntity callEntity = new CallEntity();
                callEntity.setNumber(phone.get(i));
                callEntity.setRemarkName(name);
                callEntities.add(callEntity);
            }
            callLogNeedAllBean.setCallEntities(callEntities);
            Intent intent = new Intent(getContext(), PhoneDetailAcitivty.class);
            intent.putExtra(PhoneDetailAcitivty.PHONE_DETAIL_KEY, callLogNeedAllBean);
            intent.putExtra(PhoneDetailAcitivty.PEOPLE_DETAIL_KEY, true);
            startActivity(intent);
        });
        contactList.setAdapter(contactAdapter);
        contactList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        contactList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    int firstItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
//                    View itemView=recyclerView.getChildAt(firstItem);
//                    if (itemView!=null&&itemView.getContentDescription()!=null){
//                        header.setText(itemView.getContentDescription());
//                    }
                    FriendEntity friend = contactAdapter.getItem(firstItem);
                    header.setText(friend.getFirstPinyin());
                    if (move) {
                        move = false;
                        int n = mIndex - firstItem;
                        if (n >= 0 && n < contactList.getChildCount()) {
                            int top = contactList.getChildAt(n).getTop();
                            contactList.scrollBy(0, top);
                        }
                    }
                    //itemView.getId();
//                    header.setText(itemView.getContentDescription());
                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void scrollToPosition(int position) {
        if (position >= 0 && position <= friends.size() - 1) {
            int firstItem = manager.findFirstVisibleItemPosition();
            int lastItem = manager.findLastVisibleItemPosition();
            if (position <= firstItem) {
                contactList.scrollToPosition(position);
            } else if (position <= lastItem) {
                int top = contactList.getChildAt(position - firstItem).getTop();
                contactList.scrollBy(0, top);
            } else {
                contactList.scrollToPosition(position);
                mIndex = position;
                move = true;
            }
        }
    }

    @OnClick({R.id.add, R.id.scan, R.id.search, R.id.menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add:
                // 新建
                startActivity(new Intent(getContext(), AddPeopleActivity.class));
                break;
            case R.id.scan:
                // 扫名片
                break;
            case R.id.search:
                // 搜索
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.menu:
                // 菜单
                break;
        }
    }

    @Override
    public void setPresenter(PeopleContract.Presenter presenter) {
        this.presenter = Util.checkNotNull(presenter);
    }

    @Override
    public void getFriendsSuccess(PeopleResultEntity peopleResultEntity) {
        friends = peopleResultEntity.getFriends();
        setUpUi();
    }

    @Override
    public void getFriendsFailed(String msg) {
        Util.showToast(msg);
    }
}

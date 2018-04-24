package com.damao.tongxunlu.module.select;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.damao.tongxunlu.BaseActivity;
import com.damao.tongxunlu.R;
import com.damao.tongxunlu.adapter.ContactAdapter;
import com.damao.tongxunlu.entity.FriendEntity;
import com.damao.tongxunlu.entity.PeopleResultEntity;
import com.damao.tongxunlu.module.add.AddPeopleActivity;
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
 * Created by chenlong on 2018/3/22.
 * 选择联系人
 */

public class SelectPeopleActivity extends BaseActivity implements SelectContract.View {
    @BindView(R.id.header)
    TextView header;
    @BindView(R.id.recycler)
    RecyclerView contactList;
    @BindView(R.id.pinYinSlideView)
    PinYinSlideView pinYinSlideView;
    @BindView(R.id.circleText)
    CircleTextView circleText;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private ContactAdapter contactAdapter;
    private List<FriendEntity> friends = new ArrayList<>();
    private LinearLayoutManager manager;
    private boolean move;
    private int mIndex;

    private SelectContract.Presenter presenter;
    private String newNum;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        new SelectPresenter(this, this);

        newNum = getIntent().getStringExtra(AddPeopleActivity.PHONE_KEY);

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
                        SelectPeopleActivity.this.scrollToPosition(position);
                    }
                } else {
                    SelectPeopleActivity.this.scrollToPosition(0);
                }
            }

        });
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.select_title);
    }

    @Override
    public void onResume() {
        super.onResume();
        getInfo();
    }

    private void getInfo() {
        RxPermissions rxPermissions = new RxPermissions(this);
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
        manager = new LinearLayoutManager(this);
        contactList.setLayoutManager(manager);
        contactAdapter = new ContactAdapter(this, friends);
        contactAdapter.setItemClickListener((phone, position) -> {
            // 选中联系人
            Intent intent2 = new Intent(SelectPeopleActivity.this, AddPeopleActivity.class);
            intent2.putExtra(AddPeopleActivity.FRIEND_KEY, friends.get(position));
            intent2.putExtra(AddPeopleActivity.PHONE_KEY, newNum);
            startActivity(intent2);
            finish();
        });
        contactList.setAdapter(contactAdapter);
        contactList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
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

    @OnClick({R.id.iv_back_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_click:
                finish();
                break;
        }
    }

    @Override
    public void setPresenter(SelectContract.Presenter presenter) {
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

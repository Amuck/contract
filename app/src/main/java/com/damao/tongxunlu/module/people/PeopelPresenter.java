package com.damao.tongxunlu.module.people;

import android.content.Context;
import android.text.TextUtils;

import com.damao.tongxunlu.data.repository.PeopleRepository;
import com.damao.tongxunlu.domain.PeopelInteractor;
import com.damao.tongxunlu.entity.PeopleResultEntity;
import com.damao.tongxunlu.util.Util;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by chenlong on 2018/3/8.
 *
 */

public class PeopelPresenter implements PeopleContract.Presenter{
    private Context context;
    private PeopleContract.View view;
    private PeopelInteractor peopelInteractor;

    public PeopelPresenter(Context context, PeopleContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        peopelInteractor = new PeopelInteractor(new PeopleRepository());
    }

    @Override
    public void start() {

    }

    @Override
    public void destory() {
        peopelInteractor.dispose();
        view = null;
        context = null;
    }

    @Override
    public void getFriends() {
        peopelInteractor.execute(new DisposableSubscriber<PeopleResultEntity>() {
            @Override
            public void onNext(PeopleResultEntity peopleResultEntity) {
                String result = Util.isSuccess(peopleResultEntity, context);
                if (TextUtils.isEmpty(result)) {
                    view.getFriendsSuccess(peopleResultEntity);
                } else {
                    view.getFriendsFailed(result);
                }
            }

            @Override
            public void onError(Throwable t) {
                view.getFriendsFailed("");
                t.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        }, new PeopelInteractor.RequestValues());
    }
}

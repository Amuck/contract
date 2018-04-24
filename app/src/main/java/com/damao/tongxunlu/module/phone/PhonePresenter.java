package com.damao.tongxunlu.module.phone;

import android.content.Context;
import android.text.TextUtils;

import com.damao.tongxunlu.data.repository.ContractRepository;
import com.damao.tongxunlu.domain.CallHistoryInteractor;
import com.damao.tongxunlu.entity.CllLogEntity;
import com.damao.tongxunlu.util.Util;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by chenlong on 2018/3/6.
 *
 */

public class PhonePresenter implements PhoneContract.Presenter{
    private CallHistoryInteractor callHistoryInteractor;
    private Context context;
    private PhoneContract.View view;

    PhonePresenter(Context context, PhoneContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        callHistoryInteractor = new CallHistoryInteractor(new ContractRepository());
    }

    @Override
    public void start() {

    }

    @Override
    public void destory() {
        callHistoryInteractor.dispose();
        view = null;
        context = null;
    }

    @Override
    public void getCallList() {
        callHistoryInteractor.execute(new DisposableSubscriber<CllLogEntity>() {
            @Override
            public void onNext(CllLogEntity cllLogEntity) {
                String result = Util.isSuccess(cllLogEntity, context);
                if (TextUtils.isEmpty(result)) {
                    view.getCallListSuccess(cllLogEntity.getCallLogs());
                } else {
                    view.getCallListFailed(result);
                }
            }

            @Override
            public void onError(Throwable t) {
                view.getCallListFailed("");
                t.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        }, new CallHistoryInteractor.RequestValues(-1));
    }

    @Override
    public void getCallDetail() {

    }
}

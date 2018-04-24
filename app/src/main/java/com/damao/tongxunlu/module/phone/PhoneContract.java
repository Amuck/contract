package com.damao.tongxunlu.module.phone;

import com.damao.tongxunlu.BasePresenter;
import com.damao.tongxunlu.BaseView;
import com.damao.tongxunlu.entity.CallLogNeedAllBean;

import java.util.ArrayList;

/**
 * Created by chenlong on 2018/3/6.
 *
 */

public class PhoneContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取通话记录
         */
        void getCallList();

        /**
         * 获取通话记录详情
         */
        void getCallDetail();
    }

    interface View extends BaseView<Presenter> {
        void getCallListSuccess(ArrayList<CallLogNeedAllBean> arrayList);
        void getCallListFailed(String msg);
        void getCallDetailSuccess(CallLogNeedAllBean callLogNeedAllBean);
        void getCallDetailFailed(String msg);
    }
}

package com.damao.tongxunlu.module.people;

import com.damao.tongxunlu.BasePresenter;
import com.damao.tongxunlu.BaseView;
import com.damao.tongxunlu.entity.PeopleResultEntity;

/**
 * Created by chenlong on 2018/3/8.
 *
 */

public class PeopleContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取联系人
         */
        void getFriends();
    }

    interface View extends BaseView<Presenter> {
        void getFriendsSuccess(PeopleResultEntity peopleResultEntity);
        void getFriendsFailed(String msg);
    }
}

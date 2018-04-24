package com.damao.tongxunlu.module.select;

import com.damao.tongxunlu.BasePresenter;
import com.damao.tongxunlu.BaseView;
import com.damao.tongxunlu.entity.PeopleResultEntity;

/**
 * Created by chenlong on 2018/3/22.
 *
 */

public class SelectContract {
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

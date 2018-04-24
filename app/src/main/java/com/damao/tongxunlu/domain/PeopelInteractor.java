package com.damao.tongxunlu.domain;

import com.damao.tongxunlu.BaseInteractor;
import com.damao.tongxunlu.data.repository.PeopleRepository;
import com.damao.tongxunlu.entity.PeopleResultEntity;

import io.reactivex.Flowable;

/**
 * Created by chenlong on 2018/3/8.
 * 获取联系人
 */

public class PeopelInteractor extends BaseInteractor<PeopleResultEntity, PeopelInteractor.RequestValues> {
    private PeopleRepository peopleRepository;

    public PeopelInteractor(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public Flowable<PeopleResultEntity> buildObservable(RequestValues requestValues) {
        return peopleRepository.getFriends();
    }

    public static final class RequestValues {

        public RequestValues() {
        }
    }
}

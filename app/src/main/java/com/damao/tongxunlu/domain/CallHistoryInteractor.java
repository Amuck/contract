package com.damao.tongxunlu.domain;

import com.damao.tongxunlu.BaseInteractor;
import com.damao.tongxunlu.data.repository.ContractRepository;
import com.damao.tongxunlu.entity.CllLogEntity;

import io.reactivex.Flowable;

/**
 * Created by chenlong on 2018/3/5.
 * 获取通话记录
 */

public class CallHistoryInteractor extends BaseInteractor<CllLogEntity, CallHistoryInteractor.RequestValues> {
    private ContractRepository contractRepository;

    public CallHistoryInteractor(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public Flowable<CllLogEntity> buildObservable(RequestValues requestValues) {
        return contractRepository.getCallList(requestValues.type);
    }

    public static final class RequestValues {
        private final int type;

        public RequestValues(int type) {
            this.type = type;
        }
    }
}

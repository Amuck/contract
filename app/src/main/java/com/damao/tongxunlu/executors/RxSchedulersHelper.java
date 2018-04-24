package com.damao.tongxunlu.executors;

import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chenlong on 2018/3/6.
 *
 */

public class RxSchedulersHelper {

    public static <T> FlowableTransformer<T, T> io_main() {
        return upstream -> upstream.map(t -> t)
                .subscribeOn(Schedulers.from(JobExecutor.getInstance()))
                .unsubscribeOn(Schedulers.from(JobExecutor.getInstance()))
                .observeOn(AndroidSchedulers.mainThread());
    }
}

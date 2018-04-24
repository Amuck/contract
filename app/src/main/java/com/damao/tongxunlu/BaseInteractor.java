package com.damao.tongxunlu;


import com.damao.tongxunlu.util.Util;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;


/**
 * base interactor
 *
 * @param <T>           returned value
 * @param <Params>     request params
 */
public abstract class BaseInteractor<T , Params>{
    private final CompositeDisposable disposables;

    public BaseInteractor(){
        this.disposables = new CompositeDisposable();
    }

    public abstract Flowable<T> buildObservable(Params params);

    @SuppressWarnings("unchecked")
    public void execute(DisposableSubscriber<T> observer, Params params) {
        Util.checkNotNull(observer);
        final Flowable<T> flowable =
                buildObservable(params);
        addDisposable(flowable.subscribeWith(observer));
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private void addDisposable(Disposable disposable) {
        Util.checkNotNull(disposable);
        Util.checkNotNull(disposables);
        disposables.add(disposable);
    }

}


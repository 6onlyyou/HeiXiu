package com.fushuaige.common.net.transformer;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;


public class BaseTransformer<T>
        implements ObservableTransformer<T, T> {

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.compose(new SchedulerTransformer<T>());
    }
}
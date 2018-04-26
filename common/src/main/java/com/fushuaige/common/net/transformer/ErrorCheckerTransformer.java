package com.fushuaige.common.net.transformer;

import com.fushuaige.common.base.BaseModel;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class ErrorCheckerTransformer<T extends BaseModel<R>, R>
        implements ObservableTransformer<T, R> {

    @Override
    public ObservableSource<R> apply(Observable<T> upstream) {
        return upstream.map(new Function<T, R>() {
            @Override
            public R apply(T t) throws Exception {
                if (t != null) {
                    int code = Integer.parseInt(t.getState());
                    if (code == 0) {
                        return t.getData();
                    } else {
                        String message = ErrorMessage.get(code);
                        if (message != null) {
                            throw new APIException(code, message);
                        } else {
                            throw new APIException(code, t.getMsg());
                        }
                    }
                } else {
                    return null;
                }
            }
        });
    }
}

package com.yinp.proapp.manger;

import com.yinp.proapp.web.retrofit.BaseObserver;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BaseManager {

    private CompositeDisposable compositeDisposable;

    /**
     * 解除绑定
     */
    public void detachView() {
        removeDisposable();
    }

    public void addDisposable(Observable<?> observable, BaseObserver observer) {

        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        observable.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                compositeDisposable.add(disposable);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

    }

    public void removeDisposable() {
        if (compositeDisposable != null) {
            //主动解除订阅
            compositeDisposable.dispose();
        }
    }

}

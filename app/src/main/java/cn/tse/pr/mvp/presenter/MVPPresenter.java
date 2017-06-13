package cn.tse.pr.mvp.presenter;

/**
 * Created by xieye on 2017/4/11.
 */

public class MVPPresenter<T> {
    protected T t;

    public MVPPresenter(T t) {
        this.t = t;
    }

    protected T getView() {
        return t;
    }
}

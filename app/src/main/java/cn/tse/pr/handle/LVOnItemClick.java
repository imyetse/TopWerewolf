package cn.tse.pr.handle;

/**
 * Created by xieye on 2017/6/6.
 */

public interface LVOnItemClick<T> {
    void onItemClick(int type, int position, T t);
}

package cn.tse.pr.ui.widget.zoom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by xieye on 2017/5/10.
 */

public class InternalScrollView extends ScrollView {
    private OnScrollViewChangedListener changedListener;

    public InternalScrollView(Context context) {
        super(context);
    }

    public InternalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnscrollViewChangedListener(OnScrollViewChangedListener changedListener) {
        this.changedListener = changedListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (changedListener != null) {
            changedListener.onInternalScrollChanged(l, t, oldl, oldt);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e(getClass().getSimpleName(), "onTouchEvent");
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(getClass().getSimpleName(), "onInterceptTouchEvent");

        return super.onInterceptTouchEvent(ev);
    }

    public interface OnScrollViewChangedListener {
        void onInternalScrollChanged(int left, int top, int oldLeft, int oldTop);
    }
}

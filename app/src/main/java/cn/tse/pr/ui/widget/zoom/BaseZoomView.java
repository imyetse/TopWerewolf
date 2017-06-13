package cn.tse.pr.ui.widget.zoom;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;


import cn.tse.pr.R;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;


/**
 * Created by xieye on 2017/5/10.
 */

public abstract class BaseZoomView extends LinearLayout {
    private static final float FRICTION = 2.0f;

    protected ScrollView sRootView;
    protected View sHeaderView;
    private boolean isZoomEnabled = true;
    private boolean isZooming = false;
    private boolean isParallax = true;

    protected int mHeaderHeight;
    protected int mScreenHeight;
    protected int mScreenWidth;
    private int mTouchSlop;

    private float mLastMotionY;
    private float mLastDownMotionY;
    private float mLastMotionX;
    private float mLastDownMotionX;

    private boolean mIsBeingDragged = false;

    public BaseZoomView(Context context) {
        this(context, null);
    }

    public BaseZoomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);

        ViewConfiguration config = ViewConfiguration.get(context);
        mTouchSlop = config.getScaledTouchSlop();

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        if (context instanceof Activity) {
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        }

        mScreenHeight = localDisplayMetrics.heightPixels;
        mScreenWidth = localDisplayMetrics.widthPixels;

        if (attrs != null) {

            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PullToZoomView);

            isParallax = a.getBoolean(R.styleable.PullToZoomView_parallax, true);
            isZoomEnabled = a.getBoolean(R.styleable.PullToZoomView_zoom, true);

            a.recycle();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("layout child count 2==>", getChildCount() + "//child");
        sRootView = findRootView();
        sHeaderView = findHeaderView();
    }

    @Override
    protected void attachViewToParent(View child, int index, ViewGroup.LayoutParams params) {
        super.attachViewToParent(child, index, params);

    }

    protected abstract void pullToZoom(int newScrollValue);

    protected abstract ScrollView findRootView();

    protected abstract View findHeaderView();

    protected abstract void smoothScrollToTop();

    private void pullEvent() {
        final int newScrollValue;
        final float initialMotionValue, lastMotionValue;

        initialMotionValue = mLastDownMotionY;
        lastMotionValue = mLastMotionY;

        newScrollValue = Math.round(Math.min(initialMotionValue - lastMotionValue, 0) / FRICTION);

        pullToZoom(newScrollValue);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(getClass().getSimpleName(), "OnTouchEvent");
        if (!isZoomEnabled()) {
            return false;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getEdgeFlags() != 0) {
            return false;
        }
        switch (event.getAction()) {
            case ACTION_MOVE:
                if (mIsBeingDragged) {
                    mLastMotionY = event.getY();
                    mLastMotionX = event.getX();
                    pullEvent();
                    isZooming = true;
                    return true;
                }
                break;
            case ACTION_DOWN:
                mLastMotionY = mLastDownMotionY = event.getY();
                mLastMotionX = mLastDownMotionX = event.getX();
                return true;
            //  break;
            case ACTION_UP:
            case ACTION_CANCEL:
                if (mIsBeingDragged) {
                    mIsBeingDragged = false;
                    // If we're already refreshing, just scroll back to the top
                    if (isZooming) {
                        smoothScrollToTop();
                        isZooming = false;
                        return true;
                    }
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(getClass().getSimpleName(), "onInterceptTouchEvent");
        if (!isZoomEnabled()) {
            return false;
        }

        final int action = ev.getAction();
        if (action == MotionEvent.ACTION_CANCEL || action == ACTION_UP) {
            mIsBeingDragged = false;
            return false;
        }
        if (action != ACTION_DOWN && mIsBeingDragged) {
            return true;
        }

        switch (action) {
            case ACTION_MOVE:
                final float y = ev.getY(), x = ev.getX();
                final float diffY = y - mLastMotionY;
                final float abDiffY = Math.abs(diffY);

                if (sRootView.getScrollY() == 0) {
                    if (abDiffY > mTouchSlop && diffY > 1f) {
                        mLastMotionX = x;
                        mLastMotionY = y;
                        mIsBeingDragged = true;
                    }
                }

                break;
            case ACTION_DOWN:
                mLastMotionY = mLastDownMotionY = ev.getY();
                mLastMotionX = mLastDownMotionX = ev.getX();
                mIsBeingDragged = false;

                break;
        }
        return mIsBeingDragged;
    }

    public void setZoomEnabled(boolean enabled) {
        this.isZoomEnabled = enabled;
    }

    public boolean isZoomEnabled() {
        return isZoomEnabled;
    }

    public void setParallax(boolean parallax) {
        this.isParallax = parallax;
    }

    public boolean isParallax() {
        return isParallax;
    }
}

package cn.tse.pr.ui.widget.zoom;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by xieye on 2017/5/10.
 */

public class OverZoomScrollView extends BaseZoomView {

    private AnimateRunnable mAnimateRunnable;
    private static final Interpolator sInterpolator = new Interpolator() {

        @Override
        public float getInterpolation(float input) {
            float f = input - 1.0f;
            return 1.0f + f * (f * (f * (f * f)));
        }
    };

    public OverZoomScrollView(Context context) {
        super(context);
    }

    public OverZoomScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mAnimateRunnable = new AnimateRunnable();
        if (sRootView != null) {
            ((InternalScrollView) sRootView).setOnscrollViewChangedListener(new InternalScrollView.OnScrollViewChangedListener() {
                @Override
                public void onInternalScrollChanged(int left, int top, int oldLeft, int oldTop) {
                    if (isZoomEnabled() && isParallax()) {

                    }
                }
            });
        }

    }

    @Override
    protected void pullToZoom(int newScrollValue) {
        if (mAnimateRunnable != null && !mAnimateRunnable.isFinished()) {
            mAnimateRunnable.abortAnimation();
        }

        ViewGroup.LayoutParams headerParams = sHeaderView.getLayoutParams();
        headerParams.height = Math.abs(newScrollValue) + mHeaderHeight;
        sHeaderView.setLayoutParams(headerParams);

    }

    protected ScrollView findRootView() {
        int childSize = getChildCount();
        InternalScrollView scrollView = null;
        if (childSize >= 1) {
            View view = getChildAt(0);
            if (view instanceof InternalScrollView) {
                scrollView = (InternalScrollView) view;
                if (scrollView.getChildCount() > 1) {

                }
            }
            //headerView and containerView are exist

        }
        return scrollView;
    }

    @Override
    protected View findHeaderView() {

        int childSize = getChildCount();
        InternalScrollView scrollView = null;
        if (childSize >= 1) {
            View view = getChildAt(0);
            if (view instanceof InternalScrollView) {
                scrollView = (InternalScrollView) view;
                if (scrollView.getChildCount() == 1) {
                    View viewChild = scrollView.getChildAt(0);
                    if (viewChild instanceof LinearLayout && ((ViewGroup) viewChild).getChildCount() > 1) {
                        return ((ViewGroup) viewChild).getChildAt(0);
                    }
                }
            }
            //headerView and containerView are exist

        }
        return null;
    }

    @Override
    protected void smoothScrollToTop() {
        Log.e(getClass().getSimpleName(), "smoothScrollToTop --> ");
        mAnimateRunnable.startAnimation(200L);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mHeaderHeight == 0 && sHeaderView != null) {
            mHeaderHeight = sHeaderView.getHeight();
        }
    }

    class AnimateRunnable implements Runnable {
        protected long mDuration;
        protected boolean mIsFinished = true;
        protected float mScale;
        protected long mStartTime;

        AnimateRunnable() {
        }

        public void abortAnimation() {
            mIsFinished = true;
        }

        public boolean isFinished() {
            return mIsFinished;
        }

        public void run() {
            if (sHeaderView != null) {
                float f2;
                ViewGroup.LayoutParams localLayoutParams;
                if ((!mIsFinished) && (mScale > 1.0D)) {
                    float f1 = ((float) SystemClock.currentThreadTimeMillis() - (float) mStartTime) / (float) mDuration;
                    f2 = mScale - (mScale - 1.0F) * sInterpolator.getInterpolation(f1);
                    localLayoutParams = sHeaderView.getLayoutParams();
                    Log.e(getClass().getSimpleName(), "ScalingRunnable --> f2 = " + f2);
                    if (f2 > 1.0F) {
                        localLayoutParams.height = ((int) (f2 * mHeaderHeight));
                        sHeaderView.setLayoutParams(localLayoutParams);
                        post(this);
                        return;
                    }
                    mIsFinished = true;
                }
            }
        }

        public void startAnimation(long paramLong) {
            if (sHeaderView != null) {
                mStartTime = SystemClock.currentThreadTimeMillis();
                mDuration = paramLong;
                mScale = ((float) (sHeaderView.getBottom()) / mHeaderHeight);
                mIsFinished = false;
                post(this);
            }
        }
    }
}

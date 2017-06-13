package cn.tse.pr.ui.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.kymjs.frame.presenter.ActivityPresenter;
import com.kymjs.frame.view.IDelegate;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yuzhi.fine.Lg.Lg;
import com.yuzhi.fine.common.AppManager;

import cn.tse.pr.R;
import cn.tse.pr.delegate.MainDelegate;
import cn.tse.pr.mvp.presenter.NewsPresenter;
import cn.tse.pr.mvp.presenter.NewsPresenterImpl;

/**
 * Activity基类
 *
 * @author kymjs (http://www.kymjs.com/) on 11/19/15.
 */
public abstract class BaseFrameActivity<T extends IDelegate> extends ActivityPresenter<T> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        super.onCreate(savedInstanceState);

        Lg.e("my name is BaseFrameActivity or ..." + getClass().getSimpleName());
    }

    @Override
    public void setContentView(View view) {

        // 修改状态栏颜色，4.4+生效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(view);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.translucent);//通知栏所需颜色
        super.setContentView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    @TargetApi(19)
    private void setTranslucentStatus(View rootView) {
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (rootView instanceof ViewGroup) {
            Lg.e("setTranslucentStatus ...isViewGroup");
            ViewGroup viewGroup = (ViewGroup) rootView;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View view_ = viewGroup.getChildAt(0);
                Lg.e("viewGroup id===>>>>" + view_.getId());
            }
            if (viewGroup.getChildCount() > 0) {
                Lg.e("setTranslucentStatus ...child count > 0");
                View firstView = viewGroup.getChildAt(0);
                int paddingLeft = firstView.getPaddingLeft();
                int paddingRight = firstView.getPaddingRight();
                int paddingBottom = firstView.getPaddingBottom();
                int paddingTop = firstView.getPaddingTop();
                firstView.setPadding(paddingLeft, paddingTop + getStatusBarHeight(), paddingRight, paddingBottom);
                ViewGroup.LayoutParams layoutParams = firstView.getLayoutParams();
                layoutParams.height = layoutParams.height + getStatusBarHeight();
                firstView.setLayoutParams(layoutParams);
            }
        }
    }

    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    protected int getTitleBarHeight() {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());

        if (actionBarHeight == 0 && getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    @Override
    public void onClick(View view) {

    }
}

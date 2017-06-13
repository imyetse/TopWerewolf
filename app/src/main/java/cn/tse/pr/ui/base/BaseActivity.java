package cn.tse.pr.ui.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yuzhi.fine.Lg.Lg;
import com.yuzhi.fine.common.AppManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.tse.pr.R;
import cn.tse.pr.mvp.presenter.BasePresenter;
import cn.tse.pr.mvp.presenter.NewsPresenter;
import cn.tse.pr.mvp.presenter.NewsPresenterImpl;
import cn.tse.pr.mvp.view.BaseView;
import cn.tse.pr.ui.widget.loading.ShowTools;

/**
 * Created by xieye on 2017/4/11.
 */

public class BaseActivity<T extends BasePresenter, U extends BaseView> extends AppCompatActivity {

    private Class<T> fragmentPresenter;
    protected View rootView;
    protected T presenter;

    private Class<U> fragmentView;

    public ShowTools showTools;

    protected void setMVPView() {

    }

    protected T getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        super.onCreate(savedInstanceState);
        showTools = new ShowTools(this);
        setPresenter();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {

        View view = LayoutInflater.from(this).inflate(layoutResID, null);
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

    protected boolean fitWindow() {
        return true;
    }


    @TargetApi(19)
    private void setTranslucentStatus(View rootView) {
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (fitWindow() && rootView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) rootView;
            if (viewGroup.getChildCount() > 0) {
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

    /**
     * 其实用了反射...orz
     * 要确保presenter和presenterImpl在同一文件目录下
     * 如果presenter
     */
    protected void setPresenter() {
        setMVPView();
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            Type[] typeVars = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
            if (typeVars == null || typeVars.length != 2) {
                return;
            }
            fragmentPresenter = (Class<T>) typeVars[0];
            fragmentView = (Class<U>) typeVars[1];
        } else {
            return;
        }


        try {
            //获取Class
            Class<T> clazzPresenter = (Class<T>) Class.forName(fragmentPresenter.getName() + "Impl");
            //设定构造函数的参数类型
            Class<?>[] parTypes = new Class<?>[1];
            parTypes[0] = fragmentView;
            //获取构造器
            Constructor<?> con = clazzPresenter.getConstructor(parTypes);

            //初始化构造参数
            Object[] pars = new Object[1];

            pars[0] = this;
            //构造对象
            presenter = (T) con.newInstance(pars);

        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showMsg(@StringRes int res) {
        showMsg(getString(res));
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

}

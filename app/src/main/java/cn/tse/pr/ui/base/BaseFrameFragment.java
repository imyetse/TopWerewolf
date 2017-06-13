package cn.tse.pr.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.yuzhi.fine.Lg.Lg;

import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import cn.tse.pr.mvp.presenter.BasePresenter;
import cn.tse.pr.mvp.presenter.MVPPresenter;
import cn.tse.pr.mvp.view.BaseView;

/**
 * Created by xieye on 2017/4/11.
 */

public abstract class BaseFrameFragment<T extends BasePresenter, U extends BaseView> extends Fragment {
    private Class<T> fragmentPresenter;
    protected View rootView;
    protected T presenter;

    private Class<U> fragmentView;
    protected U view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /**
     * 其实用了反射...orz
     * 要确保presenter和presenterImpl在同一文件目录下 名字也要规范哦
     */
    protected void setPresenter() {
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            Type[] typeVars = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
            if (typeVars == null || typeVars.length != 2) {
                return;
            }
            fragmentPresenter = (Class<T>) typeVars[0];
            fragmentView = (Class<U>) typeVars[1];
            Lg.e("fragmentPresenter==>"+fragmentPresenter.getName());
            Lg.e("fragmentView==>"+fragmentView.getName());
        } else {
            return;
        }


        try {
            //获取Class
            Class<T> clazzPresenter = (Class<T>) Class.forName(fragmentPresenter.getName() + "Impl");
            Lg.e("classPresenterImpl==>"+clazzPresenter.getName());
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


    protected void initView() {

    }
}

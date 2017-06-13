package cn.tse.pr.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;

import cn.tse.pr.R;
import cn.tse.pr.db.AppDao;
import cn.tse.pr.ui.base.BaseFrameActivity;
import cn.tse.pr.delegate.MainDelegate;
import cn.tse.pr.ui.my.MyActivity;
import cn.tse.pr.ui.news.SearchActivity;

/**
 * mvp activity是p层 view层代理
 * 好处是p层经常会用到上下文还有与activity相关的操作 这里能解耦
 */
public class MainActivity extends BaseFrameActivity<MainDelegate> {
    //android query 很强大很方便的utility
    AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEvent();
    }


    @Override
    protected Class<MainDelegate> getDelegateClass() {
        return MainDelegate.class;
    }

    private void initEvent() {
        aq = new AQuery(this);
        AppDao.getIntance().loadCollections();
    }

    private boolean backToQuit = false;

    @Override
    public void onBackPressed() {
        if (backToQuit) {
            backToQuit = false;
            super.onBackPressed();
        } else {
            backToQuit = true;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backToQuit = false;
                }
            }, 1000);
        }

    }
}

package cn.tse.pr.ui.news;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.tse.pr.R;
import cn.tse.pr.entity.news.TabEntity;
import cn.tse.pr.ui.base.swipebacklayout.SwipeBackActivity;

/**
 * Created by xieye on 2017.04.15.
 */

public class SearchActivity extends SwipeBackActivity {

    @Bind(R.id.et_search)
    EditText etSearch;
    NewsFragment newsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected boolean fitWindow() {
        return false;
    }

    @OnClick({R.id.tv_search, R.id.iv_back})
    public void onViewClick(View view) {
        if (view.getId() == R.id.iv_back) {
            finish();
            return;
        }
        String key = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(key)) {
            showMsg("请输入内容");
            return;
        }
        newsFragment.setKeyWord(key);
        newsFragment.getDataList(false);
    }

    private void initView() {
        newsFragment = NewsFragment.getInstance(new TabEntity(TabEntity.TAB_WEREWOLF, "狼人头条"));
        newsFragment.setAutoLoad(false);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_content, newsFragment);
        transaction.commit();
    }

}

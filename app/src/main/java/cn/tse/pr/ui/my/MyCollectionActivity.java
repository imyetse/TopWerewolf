package cn.tse.pr.ui.my;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.tse.pr.R;
import cn.tse.pr.entity.CollectionEntity;
import cn.tse.pr.mvp.presenter.CollectionPresenter;
import cn.tse.pr.mvp.view.CollectionView;
import cn.tse.pr.ui.adapter.CollectionAdapter;
import cn.tse.pr.ui.base.swipebacklayout.SwipeBackActivity;

/**
 * Created by xieye on 2017/4/14.
 */

public class MyCollectionActivity extends SwipeBackActivity<CollectionPresenter, CollectionView> implements CollectionView {

    @Bind(R.id.rv_listview)
    XRecyclerView recyclerView;

    CollectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);

        initView();
        loadData();
    }

    @OnClick({R.id.iv_back})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void initView() {
        adapter = new CollectionAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                loadData();
            }

            @Override
            public void onLoadMore() {
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.refresh();
            }
        }, 200);

    }

    private void loadData() {
        presenter.getMyCollection();
    }


    @Override
    public void onListLoaded(List<CollectionEntity> list) {
        recyclerView.refreshComplete();
        if (list == null) {
            return;
        }
        adapter.setData(list);
    }
}

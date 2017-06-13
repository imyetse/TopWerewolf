package cn.tse.pr.delegate;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kymjs.frame.view.AppDelegate;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.tse.pr.R;
import cn.tse.pr.ui.adapter.NewsAdapter;

/**
 * Created by xieye on 2017.04.10.
 */

public class NewsDelegate extends AppDelegate {
    @Bind(R.id.rv_listview)
    XRecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.create(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.layout_xrecyclerview;
    }

    @Override
    public void initWidget() {
        newsAdapter = new NewsAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsAdapter);
    }

    public void setViewClick(View.OnClickListener listener){

    }


}

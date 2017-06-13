package cn.tse.pr.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.j256.ormlite.stmt.query.In;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.tse.pr.R;
import cn.tse.pr.entity.event.OnAddTopicEvent;
import cn.tse.pr.entity.news.NewsItemEntity;
import cn.tse.pr.entity.news.TabEntity;
import cn.tse.pr.entity.topic.TopicBean;
import cn.tse.pr.handle.HtmlHandle;
import cn.tse.pr.handle.LVOnItemClick;
import cn.tse.pr.mvp.presenter.TopicPresenter;
import cn.tse.pr.mvp.view.TopicView;
import cn.tse.pr.ui.adapter.NewsAdapter;
import cn.tse.pr.ui.adapter.TopicAdapter;
import cn.tse.pr.ui.base.BaseFrameFragment;
import cn.tse.pr.ui.news.NewsFragment;
import cn.tse.pr.ui.topic.AddTopicActivity;
import cn.tse.pr.ui.topic.TopicDetailActivity;
import cn.tse.pr.ui.web.WebViewActivity;

import static cn.tse.pr.entity.news.TabEntity.TAB_FAQS;
import static cn.tse.pr.entity.news.TabEntity.TAB_HOT;
import static cn.tse.pr.entity.news.TabEntity.TAB_PLAYER;
import static cn.tse.pr.entity.news.TabEntity.TAB_SHOW;
import static cn.tse.pr.entity.news.TabEntity.TAB_VIDEO;
import static cn.tse.pr.ui.adapter.TopicAdapter.TOPIC_TPPE_AD_U2;

/**
 * Created by xieye on 2017/6/1.
 */

public class TopicFragment extends BaseFrameFragment<TopicPresenter, TopicView> implements TopicView {

    @Bind(R.id.rv_listview)
    XRecyclerView recyclerView;
    TopicAdapter topicAdapter;

    private int currentPage = 1;
    private List<TopicBean> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDataChange(OnAddTopicEvent event) {
        getDataList(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = LayoutInflater.from(this.getContext()).inflate(R.layout.layout_discuss, null);
            ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    @OnClick({R.id.ll_add_word_action, R.id.ll_add_pic_action})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_word_action:
                AddTopicActivity.startActivity(this.getContext(), false);
                getActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom);
                break;
            case R.id.ll_add_pic_action:
                AddTopicActivity.startActivity(this.getContext(), true);
                getActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom);
                break;
        }
    }

    @Override
    protected void initView() {
        if (topicAdapter != null) {
            return;
        }
        topicAdapter = new TopicAdapter(this.getContext());
        topicAdapter.setOnItemClick(new LVOnItemClick<TopicBean>() {
            @Override
            public void onItemClick(int type, int position, TopicBean topicBean) {
                presenter.joinTopic(topicBean.getId(), topicBean.getJoinCount());
                if (type == TOPIC_TPPE_AD_U2) {
                    WebViewActivity.openWebView(TopicFragment.this.getContext(), topicBean.getRedirectUrl(), topicBean.getTopicTitle());
                } else {
                    TopicDetailActivity.startActivity(TopicFragment.this.getContext(), JSON.toJSONString(topicBean));
                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(topicAdapter);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getDataList(false);
            }

            @Override
            public void onLoadMore() {
                getDataList(true);
            }
        });

        getDataList(false);
    }


    public void getDataList(boolean more) {
        if (more) {
            currentPage++;
        } else {
            currentPage = 1;
        }

        presenter.getTopic(currentPage);
    }

    @Override
    public void onTopicGet(List<TopicBean> topics) {
        recyclerView.refreshComplete();
        if (topics == null) {
            return;
        }
        if (topics.size() < 10) {
            recyclerView.setLoadingMoreEnabled(false);
        } else {
            recyclerView.setLoadingMoreEnabled(true);
        }
        if (currentPage == 1) {
            list.clear();
            list.addAll(topics);
        } else {
            list.addAll(topics);
        }
        topicAdapter.setDataList(list);
    }
}

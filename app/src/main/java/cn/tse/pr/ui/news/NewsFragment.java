package cn.tse.pr.ui.news;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.yuzhi.fine.Lg.Lg;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.tse.pr.R;
import cn.tse.pr.entity.news.NewsItemEntity;
import cn.tse.pr.handle.HtmlHandle;
import cn.tse.pr.ui.adapter.NewsAdapter;
import cn.tse.pr.ui.adapter.YKVideoAdapter;
import cn.tse.pr.ui.base.BaseFrameFragment;
import cn.tse.pr.delegate.NewsDelegate;
import cn.tse.pr.entity.news.TabEntity;
import cn.tse.pr.mvp.presenter.NewsPresenter;
import cn.tse.pr.mvp.view.NewsView;
import cn.tse.pr.ui.web.WebViewActivity;

import static cn.tse.pr.entity.news.TabEntity.TAB_FAQS;
import static cn.tse.pr.entity.news.TabEntity.TAB_HOT;
import static cn.tse.pr.entity.news.TabEntity.TAB_PLAYER;
import static cn.tse.pr.entity.news.TabEntity.TAB_SHOW;
import static cn.tse.pr.entity.news.TabEntity.TAB_VIDEO;

/**
 * Created by xieye on 2017/4/10.
 */

public class NewsFragment extends BaseFrameFragment<NewsPresenter, NewsView> implements View.OnClickListener, NewsView {

    private final static int PAGESIZE = 20;
    @Bind(R.id.rv_listview)
    XRecyclerView recyclerView;

    private NewsAdapter newsAdapter;
    private TabEntity tabEntity;
    private List<NewsItemEntity> list;
    public static String currentId = TabEntity.TAB_WEREWOLF;
    private int currentPage = 1;

    public static NewsFragment getInstance(TabEntity tabEntity) {
        Bundle bundle = new Bundle();
        bundle.putString("tab", JSON.toJSONString(tabEntity));
        NewsFragment newsFragment = new NewsFragment();
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.layout_xrecyclerview, null);
            ButterKnife.bind(this, rootView);
        }

        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (presenter != null && currentId.equals(tabEntity.getId())) {
            //之前初始化过了 在当前页
            if (list == null || list.size() == 0) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.refresh();
                    }
                }, 200);
            } else {
                newsAdapter.setDatas(list);
            }
        }
    }


    @Override
    protected void initView() {
        if (newsAdapter != null) {
            return;
        }
        newsAdapter = new NewsAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(newsAdapter);
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
        if (tabEntity.getId().equals(TabEntity.TAB_WEREWOLF) && autoLoad) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.refresh();
                }
            }, 200);
        }
        newsAdapter.setOnNewsClick(new NewsAdapter.OnNewsClick() {
            @Override
            public void OnItemClick(NewsItemEntity entity) {
                if (tabEntity.getId().equals(TAB_VIDEO)) {
                    HtmlHandle.openJRTTVideo(NewsFragment.this.getActivity(), entity.getLink(), entity.getTitle());
                } else if (tabEntity.getId().equals(TAB_SHOW)) {
                    presenter.getYKVideoPlayList(entity.getLink());
                } else {
                    WebViewActivity.openWebView(NewsFragment.this.getActivity(), entity.getLink(), entity.getTitle());
                }
            }
        });
    }

    public void getDataList(boolean more) {
        if (more) {
            currentPage++;
        } else {
            currentPage = 1;
        }
        if (tabEntity == null) {
            return;
        }
        switch (tabEntity.getId()) {
            case TabEntity.TAB_WEREWOLF:
                presenter.getNewsFromJRRT(keyWord, (currentPage - 1) * PAGESIZE, PAGESIZE, 1);
                break;
            case TAB_HOT:
                // presenter.getNewsFromSH("67121", currentPage, PAGESIZE);
                presenter.getNewsFromBD(keyWord, (currentPage - 1) * PAGESIZE);
                break;
            case TAB_VIDEO:
                presenter.getNewsFromJRRT(keyWord, (currentPage - 1) * PAGESIZE, PAGESIZE, 2);
                break;
            case TAB_FAQS:
                presenter.getNewsFromZH(currentPage);
                break;
            case TAB_PLAYER:
                presenter.getNewsFromDB();
                break;
            case TAB_SHOW:
                presenter.getYKVideoAlbums(keyWord);
                break;
        }
    }

    private String keyWord;
    private boolean autoLoad = true;

    public void setAutoLoad(boolean autoLoad) {
        this.autoLoad = autoLoad;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String tab = bundle.getString("tab");
            if (!TextUtils.isEmpty(tab)) {
                TabEntity tabEntity = JSONObject.parseObject(tab, TabEntity.class);
                this.tabEntity = tabEntity;
                switch (tabEntity.getId()) {
                    case TabEntity.TAB_WEREWOLF:
                        break;
                    case TAB_HOT:
                        break;
                    case TAB_VIDEO:
                        break;
                    case TAB_FAQS:
                        break;
                }
            }

        }
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onListLoaded(List<NewsItemEntity> list, int type) {
        if (type == FAIL) {
            recyclerView.refreshComplete();
            return;
        }
        if (type == YK_PLAYLIST) {
            YKVideoAdapter adapter = new YKVideoAdapter();
            adapter.setDatas(list);
            DialogPlus dialog = DialogPlus.newDialog(this.getActivity())
                    .setAdapter(adapter)
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                            NewsItemEntity entity = (NewsItemEntity) item;
                            HtmlHandle.openYKVideo(NewsFragment.this.getActivity(), entity.getLink(), entity.getTitle());
                        }
                    })
                    .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                    .create();
            dialog.show();
            return;
        }
        if (currentPage == 1) {
            this.list = list;
        } else {
            this.list.addAll(list);
        }

        if (type >= 0) {
            //有可能是获取缓存
            recyclerView.refreshComplete();
        }
        newsAdapter.setDatas(this.list);

    }
}

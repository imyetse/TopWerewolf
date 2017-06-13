package cn.tse.pr.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.tse.pr.R;
import cn.tse.pr.entity.BaseEntity;
import cn.tse.pr.entity.news.NewsItemEntity;
import cn.tse.pr.entity.news.TabEntity;
import cn.tse.pr.mvp.presenter.NewsPresenter;
import cn.tse.pr.mvp.view.NewsView;
import cn.tse.pr.ui.base.BaseFrameFragment;
import cn.tse.pr.ui.my.MyActivity;
import cn.tse.pr.ui.news.NewsFragment;
import cn.tse.pr.ui.news.SearchActivity;

/**
 * Created by xieye on 2017.04.15.
 */

public class HomeFragment extends BaseFrameFragment<NewsPresenter, NewsView> implements View.OnClickListener, NewsView {

    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.magic_indicator)
    MagicIndicator magicIndicator;

    private List<TabEntity> mTabs = new ArrayList<>();
    private List<BaseEntity> mDataList = new ArrayList<>();
    private List<Fragment> mViews = new ArrayList<>();
    private PagerAdapter pagerAdapter;

    private boolean isInit = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = LayoutInflater.from(this.getContext()).inflate(R.layout.layout_main_home, null);
            ButterKnife.bind(this, rootView);
        }
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isInit) {
            isInit = true;
            initWidget();
        }
    }


    @OnClick({R.id.iv_search})
    public void onViewOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }
    }


    public void initWidget() {
        initTabs();
        initFragment();
        initIndicator();
    }

    private void initFragment() {
        for (TabEntity entity : mTabs) {
            mViews.add(NewsFragment.getInstance(entity));
        }

        pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mViews.get(position);
            }

            @Override
            public int getCount() {
                return mViews.size();
            }
        };
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                NewsFragment.currentId = mTabs.get(position).getId();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initIndicator() {
        magicIndicator.setBackgroundColor(Color.parseColor("#F8F8F8"));
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setSkimOver(true);
        commonNavigator.setLeftPadding(UIUtil.dip2px(this.getActivity(), 10));

        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTabs == null ? 0 : mTabs.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(mTabs.get(index).getTitle());
                clipPagerTitleView.setTextColor(Color.parseColor("#666666"));
                clipPagerTitleView.setClipColor(Color.parseColor("#488DFF"));
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewsFragment.currentId = mTabs.get(index).getId();
                        mViewPager.setCurrentItem(index, false);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }


    private void initTabs() {
        mTabs.add(new TabEntity(TabEntity.TAB_WEREWOLF, "狼人头条"));
        mTabs.add(new TabEntity(TabEntity.TAB_HOT, "热门"));
        mTabs.add(new TabEntity(TabEntity.TAB_VIDEO, "视频"));
        mTabs.add(new TabEntity(TabEntity.TAB_FAQS, "问答"));
        mTabs.add(new TabEntity(TabEntity.TAB_SHOW, "节目"));
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onListLoaded(List<NewsItemEntity> list, int type) {

    }
}

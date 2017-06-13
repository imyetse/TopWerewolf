package cn.tse.pr.delegate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.kymjs.frame.view.AppDelegate;

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
import cn.tse.pr.entity.news.TabEntity;
import cn.tse.pr.ui.main.HomeFragment;
import cn.tse.pr.ui.main.MainActivity;
import cn.tse.pr.ui.main.TopicFragment;
import cn.tse.pr.ui.my.MyActivity;
import cn.tse.pr.ui.news.NewsFragment;
import cn.tse.pr.ui.news.SearchActivity;
import cn.tse.pr.utils.QiNiuHelper;

/**
 * Created by xieye on 2017/4/11.
 */

public class MainDelegate extends AppDelegate {

    // 定义FragmentTabHost对象 
    @Bind(R.id.tabHost)
    FragmentTabHost tabHost;
    // 定义一个布局 
    private LayoutInflater layoutInflater;
    // 定义数组来存放按钮图片 
    private int tabIcons[] = {R.drawable.selector_main_tab_home, R.drawable.selector_main_tab_discuss};
    // Tab选项卡的文字 
    private String tabTexts[] = {"头条", "讨论"};
    // 定义数组来存放Fragment界面 
    private Class tabViews[] = {HomeFragment.class, TopicFragment.class};

    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.create(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initWidget() {
        initFragment();
    }


    @OnClick({R.id.iv_my})
    public void onViewOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_my:
                getActivity().startActivity(new Intent(getActivity(), MyActivity.class));
                break;
        }
    }


    private void initFragment() {
        QiNiuHelper.getHelper();
        // 实例化布局对象 
        layoutInflater = LayoutInflater.from(this.getActivity());
        // 实例化TabHost对象，得到TabHost 
        tabHost.setup(getActivity(), ((MainActivity) getActivity()).getSupportFragmentManager(), R.id.container);
        // 判断版本4.0以上去掉tab标签间隙
        tabHost.getTabWidget().removeAllViews();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            tabHost.getTabWidget().setDividerDrawable(R.color.white);
        }
        for (int i = 0; i < tabTexts.length; i++) {
            // 为每一个Tab按钮设置图标、文字和内容 
            final TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabTexts[i]).setIndicator(
                    getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            tabHost.addTab(tabSpec, tabViews[i], null);
        }
    }


    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.item_main_tab, null);
        ImageView icon = (ImageView) view.findViewById(R.id.iv_main_tab);
        icon.setImageResource(tabIcons[index]);
        TextView textView = (TextView) view.findViewById(R.id.tv_main_tab);
        textView.setText(tabTexts[index]);
        return view;
    }

}

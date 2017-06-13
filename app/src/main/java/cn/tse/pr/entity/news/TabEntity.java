package cn.tse.pr.entity.news;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import cn.tse.pr.entity.BaseEntity;

/**
 * Created by xieye on 2017/4/10.
 */

public class TabEntity extends BaseEntity {

    /**
     * ?offset=0&format=json&keyword=%E7%8B%BC%E4%BA%BA%E6%9D%80&autoload=true&count=20&cur_tab=1
     * 今日头条
     */
    private static String jrttUrl = "http://www.toutiao.com/search_content/";
    /**
     * 搜狐标签
     */
    private static String shUrl = "http://mt.sohu.com/tags/67121.shtml";
    /**
     * 知乎问答
     */
    private static String zhUrl = "https://www.zhihu.com/topic/19846199/top-answers";
    /**
     * 豆瓣狼人杀小组
     */
    private static String dbUrl = "https://www.douban.com/search?q=%E7%8B%BC%E4%BA%BA%E6%9D%80";


    public final static String TAB_WEREWOLF = "werewolf";
    public final static String TAB_HOT = "hot";
    public final static String TAB_VIDEO = "video";
    public final static String TAB_FAQS = "faqs";
    public final static String TAB_PLAYER = "player";
    public final static String TAB_SHOW = "show";
    public final static String TAB_DISCUSS = "discuss";

    private List<BaseEntity> mDatas = new ArrayList<>();
    private Fragment fragment;

    public List<BaseEntity> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<BaseEntity> mDatas) {
        this.mDatas = mDatas;
    }

    public TabEntity(){}

    public TabEntity(String id, String title) {
        this.id = id;
        this.title = title;
        switch (id) {
            case TAB_WEREWOLF:
                url = jrttUrl;
                break;
            case TAB_HOT:
                url = shUrl;
                break;
            case TAB_VIDEO:
                url = jrttUrl;
                break;
            case TAB_PLAYER:
                url = dbUrl;
                break;
            case TAB_FAQS:
                url = zhUrl;
                break;
        }
    }

    private String title;
    private String id;
    private String url;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

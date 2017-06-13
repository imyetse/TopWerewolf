package cn.tse.pr.mvp.view;

import org.jsoup.Connection;

import java.util.List;

import cn.tse.pr.entity.news.NewsItemEntity;

/**
 * Created by xieye on 2017/4/11.
 */

public interface NewsView extends BaseView {
    final static int FAIL = -2;
    final static int LOCAL = -1;
    final static int JRTT = 0;
    final static int SH = 2;
    final static int ZH = 1;
    final static int DB = 3;
    final static int YK_ALBUM = 4;
    final static int YK_PLAYLIST = 5;
    final static int YK_VIDEO = 6;

    void onListLoaded(List<NewsItemEntity> list, int type);
}

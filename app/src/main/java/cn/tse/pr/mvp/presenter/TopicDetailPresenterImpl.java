package cn.tse.pr.mvp.presenter;

import cn.tse.pr.mvp.view.TopicDetailView;
import cn.tse.pr.mvp.view.TopicView;

/**
 * Created by xieye on 2017/6/8.
 */

public class TopicDetailPresenterImpl extends TopicPresenterImpl<TopicDetailView> implements TopicDetailPresenter{
    public TopicDetailPresenterImpl(TopicDetailView topicView) {
        super(topicView);
    }
}

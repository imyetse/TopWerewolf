package cn.tse.pr.mvp.view;

import java.util.List;

import cn.tse.pr.entity.topic.TopicBean;

/**
 * Created by xieye on 2017/6/3.
 */

public interface TopicView extends BaseView {
    void onTopicGet(List<TopicBean> topics);
}

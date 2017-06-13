package cn.tse.pr.mvp.view;

import java.util.List;

import cn.tse.pr.entity.topic.CommentBean;

/**
 * Created by xieye on 2017/6/6.
 */

public interface TopicDetailView extends TopicView {
    void onGetComment(List<CommentBean> list);

    void onComment(boolean success, CommentBean commentBean);

    void onGetLike(List<CommentBean> commentWithLikes);
}

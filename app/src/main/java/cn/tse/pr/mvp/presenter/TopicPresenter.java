package cn.tse.pr.mvp.presenter;

import java.util.List;

import cn.tse.pr.entity.topic.CommentBean;
import cn.tse.pr.entity.topic.TopicBean;

/**
 * Created by xieye on 2017/6/3.
 */

public interface TopicPresenter extends BasePresenter {
    void getTopic(int page);

    void addTopic(String title, String content, String displayUrl, int type);

    void getTopicDetail(String topicId);

    void getTopicComment(String topicId);

    void commentTopic(CommentBean commentBean);

    /**
     * 这个就厉害了
     *
     * @param topicId
     */
    void getLike(List<CommentBean> commentList, String topicId);

    void likeComment(String commentId, String topicId, int likeNum);

    void joinTopic(String topicId, int joinCount);
}

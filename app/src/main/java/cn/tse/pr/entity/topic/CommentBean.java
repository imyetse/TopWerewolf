package cn.tse.pr.entity.topic;

import android.text.method.BaseMovementMethod;

import com.avos.avoscloud.AVUser;

import cn.tse.pr.entity.BaseEntity;

/**
 * Created by xieye on 2017/6/7.
 */

public class CommentBean extends BaseEntity {
    private String id;
    private boolean like;
    private String title;
    private String content;
    private TopicBean topicBean;
    private AVUser user;
    private AVUser replyUser;//被回复的人
    private String createAt;
    private int likeNum;

    public AVUser getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(AVUser replyUser) {
        this.replyUser = replyUser;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TopicBean getTopicBean() {
        return topicBean;
    }

    public void setTopicBean(TopicBean topicBean) {
        this.topicBean = topicBean;
    }

    public AVUser getUser() {
        return user;
    }

    public void setUser(AVUser user) {
        this.user = user;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}

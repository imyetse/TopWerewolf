package cn.tse.pr.entity.topic;

import com.avos.avoscloud.AVUser;

import cn.tse.pr.entity.BaseEntity;

/**
 * Created by xieye on 2017/6/5.
 */

public class TopicBean extends BaseEntity{
    private String id;
    private String topicTitle;
    private String topicDetail;
    private String displayUrl;
    private String redirectUrl;
    private int joinCount;
    private AVUser avUser;
    private int type;

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AVUser getAvUser() {
        return avUser;
    }

    public void setAvUser(AVUser avUser) {
        this.avUser = avUser;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getTopicDetail() {
        return topicDetail;
    }

    public void setTopicDetail(String topicDetail) {
        this.topicDetail = topicDetail;
    }

    public int getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }
}

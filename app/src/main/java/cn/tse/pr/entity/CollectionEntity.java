package cn.tse.pr.entity;

import com.avos.avoscloud.AVObject;

import org.jsoup.Connection;

/**
 * Created by xieye on 2017/4/14.
 */

public class CollectionEntity extends BaseEntity {

    private String title;
    private String link;
    private String uid;
    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

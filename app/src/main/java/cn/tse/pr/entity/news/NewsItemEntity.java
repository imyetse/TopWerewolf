package cn.tse.pr.entity.news;

import java.util.List;

import cn.tse.pr.entity.BaseEntity;

/**
 * 新闻列表的内容
 * <p>
 * Created by xieye on 2017/4/6.
 */

public class NewsItemEntity extends BaseEntity {
    /**
     * 标题
     */
    private String title;
    /**
     * 链接
     */
    private String link;
    /**
     * 图集
     */
    private List<String> imgs;
    /**
     * 单张图片
     */
    private String img;
    /**
     * 作者
     */
    private String author;
    /**
     * 时间
     */
    private String timeStr;
    /**
     * 评论数量
     */
    private String commentCount;
    /**
     * 这个是知乎才有的
     */
    private String agreeCount;
    /**
     * 视频封面
     */
    private boolean hasVideo;
    /**
     * 视频时长
     */
    private String videoDurationStr;

    public String getVideoDurationStr() {
        return videoDurationStr;
    }

    public void setVideoDurationStr(String videoDurationStr) {
        this.videoDurationStr = videoDurationStr;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getAgreeCount() {
        return agreeCount;
    }

    public void setAgreeCount(String agreeCount) {
        this.agreeCount = agreeCount;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}

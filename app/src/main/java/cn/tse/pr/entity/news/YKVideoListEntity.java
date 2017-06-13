package cn.tse.pr.entity.news;

import java.util.List;

import cn.tse.pr.entity.BaseEntity;

/**
 * 视频列表
 * <p>
 * Created by xieye on 2017/4/14.
 */

public class YKVideoListEntity extends BaseEntity {

    private List<ResultListBean> resultList;

    public List<ResultListBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<ResultListBean> resultList) {
        this.resultList = resultList;
    }

    public static class ResultListBean {
        private List<ShowsBean> shows;

        public List<ShowsBean> getShows() {
            return shows;
        }

        public void setShows(List<ShowsBean> shows) {
            this.shows = shows;
        }

    }

    public static class ShowsBean {
        /**
         * create_time : 1485085806
         * view_type : 2
         * cate_id : -21
         * videoid : XMjQ4MjAzODQxMg==
         * title : PandaKill狼人杀第二季----第一期PART1
         * total_vv : 5.6万
         * duration : 01:13:20
         * publish_time : 2月前
         * thumburl : http://r1.ykimg.com/054104085884B0606F0A86232B4F2920
         * username : 爱是风看见海
         */

        private int create_time;
        private int view_type;
        private int cate_id;
        private String videoid;
        private String title;
        private String total_vv;
        private String duration;
        private String publish_time;
        private String thumburl;
        private String username;

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getView_type() {
            return view_type;
        }

        public void setView_type(int view_type) {
            this.view_type = view_type;
        }

        public int getCate_id() {
            return cate_id;
        }

        public void setCate_id(int cate_id) {
            this.cate_id = cate_id;
        }

        public String getVideoid() {
            return videoid;
        }

        public void setVideoid(String videoid) {
            this.videoid = videoid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTotal_vv() {
            return total_vv;
        }

        public void setTotal_vv(String total_vv) {
            this.total_vv = total_vv;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getThumburl() {
            return thumburl;
        }

        public void setThumburl(String thumburl) {
            this.thumburl = thumburl;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}

package cn.tse.pr.entity.news;

import java.util.List;

import cn.tse.pr.entity.BaseEntity;

/**
 * 专辑列表
 * Created by xieye on 2017/4/14.
 */

public class YKVideoAlbumListEntity extends BaseEntity {

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * playlistid : PMTE1NDg2MjQw
         */

        private String title;
        private String publish_time;
        private String username;
        private String thumburl;
        private String playlistid;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getThumburl() {
            return thumburl;
        }

        public void setThumburl(String thumburl) {
            this.thumburl = thumburl;
        }

        public String getPlaylistid() {
            return playlistid;
        }

        public void setPlaylistid(String playlistid) {
            this.playlistid = playlistid;
        }
    }
}

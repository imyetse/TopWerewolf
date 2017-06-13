package cn.tse.pr.entity.news;

import java.util.List;

import cn.tse.pr.entity.BaseEntity;

/**
 * 优酷的视频息
 * Created by xieye on 2017/4/14.
 */

public class YKVideoEntity extends BaseEntity{

    /**
     * data : {"stream":[{"segs":[{"cdn_url":"http://k.youku.com/player/getFlvPath/sid/0492151113731120f6284/st/mp4/fileid/03002001005884E1C383342F0444BF92F5C1DF-DE1B-34EE-7B50-4865FAA8ADF9?k=f78486c3d07873832412f943&hd=1&myp=0&ts=5221&ctype=12&ev=1&token=0509&oip=977207309&ep=ciadGEiIV88B5iTciT8bZ3rkdn8LXP4J9h%2BHgdJjALshQOC77D%2BixJWzTftAYPkddiZ0ZuLz3dWU%0AazRiYYQ2qBoQ3ErdTPrnjPXj5albwpV2F2g%2BA7mhvVSeRjD4&ccode=01010101&duration=5221&expire=18000&psid=48ac4061e20576af11f1783be0260de8&ups_client_netip=58.63.0.13&ups_ts=1492151113&ups_userid=&utid=&vid=XMjQ4MjI4Mzg0MA%3D%3D&vkey=A57493b7d89b0486b23570a2cdb1d6ebd"}]}],"uploader":{"avatar":{"big":"https://r1.ykimg.com/0130391F4556FD1050BC312F0444BFAE0C1952-D5AC-129F-765D-E72C6C7E4E0D","middle":"https://r1.ykimg.com/0130391F4556FD1051F61F2F0444BF2C686F97-27C4-CDAD-8F1E-8B8B425B291D","large":"https://r1.ykimg.com/0130391F4556FD10509A202F0444BF7DE5F44A-FE96-F394-54B9-7626B2BD9BB6"},"homepage":"http://i.youku.com/u/UMzE1NTIzNTU4MA==","username":"爱是风看见海"},"video":{"title":"PandaKill狼人杀第二季----第一期PART3","seconds":5221,"weburl":"http://v.youku.com/v_show/id_XMjQ4MjI4Mzg0MA==.html","logo":"https://r1.ykimg.com/054104085884E2F76F0A914D77962F94","username":"爱是风看见海"}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * stream : [{"segs":[{"cdn_url":"http://k.youku.com/player/getFlvPath/sid/0492151113731120f6284/st/mp4/fileid/03002001005884E1C383342F0444BF92F5C1DF-DE1B-34EE-7B50-4865FAA8ADF9?k=f78486c3d07873832412f943&hd=1&myp=0&ts=5221&ctype=12&ev=1&token=0509&oip=977207309&ep=ciadGEiIV88B5iTciT8bZ3rkdn8LXP4J9h%2BHgdJjALshQOC77D%2BixJWzTftAYPkddiZ0ZuLz3dWU%0AazRiYYQ2qBoQ3ErdTPrnjPXj5albwpV2F2g%2BA7mhvVSeRjD4&ccode=01010101&duration=5221&expire=18000&psid=48ac4061e20576af11f1783be0260de8&ups_client_netip=58.63.0.13&ups_ts=1492151113&ups_userid=&utid=&vid=XMjQ4MjI4Mzg0MA%3D%3D&vkey=A57493b7d89b0486b23570a2cdb1d6ebd"}]}]
         * uploader : {"avatar":{"big":"https://r1.ykimg.com/0130391F4556FD1050BC312F0444BFAE0C1952-D5AC-129F-765D-E72C6C7E4E0D","middle":"https://r1.ykimg.com/0130391F4556FD1051F61F2F0444BF2C686F97-27C4-CDAD-8F1E-8B8B425B291D","large":"https://r1.ykimg.com/0130391F4556FD10509A202F0444BF7DE5F44A-FE96-F394-54B9-7626B2BD9BB6"},"homepage":"http://i.youku.com/u/UMzE1NTIzNTU4MA==","username":"爱是风看见海"}
         * video : {"title":"PandaKill狼人杀第二季----第一期PART3","seconds":5221,"weburl":"http://v.youku.com/v_show/id_XMjQ4MjI4Mzg0MA==.html","logo":"https://r1.ykimg.com/054104085884E2F76F0A914D77962F94","username":"爱是风看见海"}
         */

        private UploaderBean uploader;
        private VideoBean video;
        private List<StreamBean> stream;

        public UploaderBean getUploader() {
            return uploader;
        }

        public void setUploader(UploaderBean uploader) {
            this.uploader = uploader;
        }

        public VideoBean getVideo() {
            return video;
        }

        public void setVideo(VideoBean video) {
            this.video = video;
        }

        public List<StreamBean> getStream() {
            return stream;
        }

        public void setStream(List<StreamBean> stream) {
            this.stream = stream;
        }

        public static class UploaderBean {
            /**
             * avatar : {"big":"https://r1.ykimg.com/0130391F4556FD1050BC312F0444BFAE0C1952-D5AC-129F-765D-E72C6C7E4E0D","middle":"https://r1.ykimg.com/0130391F4556FD1051F61F2F0444BF2C686F97-27C4-CDAD-8F1E-8B8B425B291D","large":"https://r1.ykimg.com/0130391F4556FD10509A202F0444BF7DE5F44A-FE96-F394-54B9-7626B2BD9BB6"}
             * homepage : http://i.youku.com/u/UMzE1NTIzNTU4MA==
             * username : 爱是风看见海
             */

            private AvatarBean avatar;
            private String homepage;
            private String username;

            public AvatarBean getAvatar() {
                return avatar;
            }

            public void setAvatar(AvatarBean avatar) {
                this.avatar = avatar;
            }

            public String getHomepage() {
                return homepage;
            }

            public void setHomepage(String homepage) {
                this.homepage = homepage;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public static class AvatarBean {
                /**
                 * big : https://r1.ykimg.com/0130391F4556FD1050BC312F0444BFAE0C1952-D5AC-129F-765D-E72C6C7E4E0D
                 * middle : https://r1.ykimg.com/0130391F4556FD1051F61F2F0444BF2C686F97-27C4-CDAD-8F1E-8B8B425B291D
                 * large : https://r1.ykimg.com/0130391F4556FD10509A202F0444BF7DE5F44A-FE96-F394-54B9-7626B2BD9BB6
                 */

                private String big;
                private String middle;
                private String large;

                public String getBig() {
                    return big;
                }

                public void setBig(String big) {
                    this.big = big;
                }

                public String getMiddle() {
                    return middle;
                }

                public void setMiddle(String middle) {
                    this.middle = middle;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }
            }
        }

        public static class VideoBean {
            /**
             * title : PandaKill狼人杀第二季----第一期PART3
             * seconds : 5221
             * weburl : http://v.youku.com/v_show/id_XMjQ4MjI4Mzg0MA==.html
             * logo : https://r1.ykimg.com/054104085884E2F76F0A914D77962F94
             * username : 爱是风看见海
             */

            private String title;
            private int seconds;
            private String weburl;
            private String logo;
            private String username;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public String getWeburl() {
                return weburl;
            }

            public void setWeburl(String weburl) {
                this.weburl = weburl;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }

        public static class StreamBean {
            private List<SegsBean> segs;

            public List<SegsBean> getSegs() {
                return segs;
            }

            public void setSegs(List<SegsBean> segs) {
                this.segs = segs;
            }

            public static class SegsBean {
                /**
                 * cdn_url : http://k.youku.com/player/getFlvPath/sid/0492151113731120f6284/st/mp4/fileid/03002001005884E1C383342F0444BF92F5C1DF-DE1B-34EE-7B50-4865FAA8ADF9?k=f78486c3d07873832412f943&hd=1&myp=0&ts=5221&ctype=12&ev=1&token=0509&oip=977207309&ep=ciadGEiIV88B5iTciT8bZ3rkdn8LXP4J9h%2BHgdJjALshQOC77D%2BixJWzTftAYPkddiZ0ZuLz3dWU%0AazRiYYQ2qBoQ3ErdTPrnjPXj5albwpV2F2g%2BA7mhvVSeRjD4&ccode=01010101&duration=5221&expire=18000&psid=48ac4061e20576af11f1783be0260de8&ups_client_netip=58.63.0.13&ups_ts=1492151113&ups_userid=&utid=&vid=XMjQ4MjI4Mzg0MA%3D%3D&vkey=A57493b7d89b0486b23570a2cdb1d6ebd
                 */

                private String cdn_url;

                public String getCdn_url() {
                    return cdn_url;
                }

                public void setCdn_url(String cdn_url) {
                    this.cdn_url = cdn_url;
                }
            }
        }
    }
}

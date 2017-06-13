package cn.tse.pr.entity.news;

import java.util.List;

import cn.tse.pr.entity.BaseEntity;

/**
 * Created by xieye on 2017/4/13.
 */

public class JRTTVideoEntity extends BaseEntity{

    /**
     * data : {"status":10,"user_id":"toutiao","video_id":"65dee91dcc364dec9301028c6fc60715","big_thumbs":[{"img_num":99,"img_url":"https://p1.pstatp.com/origin/19c80005ce37e46715a5","img_x_size":160,"img_y_size":90,"img_x_len":1,"img_y_len":99}],"video_duration":2806.92,"video_list":{"video_1":{"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vNzJhYThlMTY0MjI4ZjY0MWQ0YWQzOTQ1ZGYzZTNmMmEvNThlZWY3MjMvdmlkZW8vbS8yMjBiMTI4MjMzMjMyNDQ0NzEyYWI3MzFkN2I3Njk4NmU1OTExNDQ4YWYwMDAwMjI3MTkwNGY2MDljLw==","bitrate":135315,"definition":"360p","main_url":"aHR0cDovL3Y2LjM2NXlnLmNvbS92aWRlby9tLzIyMGIxMjgyMzMyMzI0NDQ3MTJhYjczMWQ3Yjc2OTg2ZTU5MTE0NDhhZjAwMDAyMjcxOTA0ZjYwOWMvP0V4cGlyZXM9MTQ5MjA1OTQ0MyZBV1NBY2Nlc3NLZXlJZD1xaDBoOVRkY0VNb1Myb1BqN2FLWCZTaWduYXR1cmU9eHpjNTg3eGVtc2dBM0NHOVZkS21hN3FIRUhRJTNE","preload_interval":25,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"size":7.1097334E7,"socket_buffer":8.1189E7,"user_video_proxy":1,"vheight":360,"vtype":"mp4","vwidth":640},"video_2":{"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vMzJiZTg3ZDI1N2VjZmNiOWJjZThjN2ViMWFiNzA3ZWYvNThlZWY3MjMvdmlkZW8vbS8yMjA1ZDc1MmM0MmUyZTM0Njk3YjkzMjgxMWM1ZDllZGI0YjExNDRiYjIwMDAwMmIzYjg2MDM3NDhlLw==","bitrate":213144,"definition":"480p","main_url":"aHR0cDovL3YzLjM2NXlnLmNvbS8zMmJlODdkMjU3ZWNmY2I5YmNlOGM3ZWIxYWI3MDdlZi81OGVlZjcyMy92aWRlby9tLzIyMDVkNzUyYzQyZTJlMzQ2OTdiOTMyODExYzVkOWVkYjRiMTE0NGJiMjAwMDAyYjNiODYwMzc0OGUv","preload_interval":25,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"size":9.8414714E7,"socket_buffer":1.278864E8,"user_video_proxy":1,"vheight":480,"vtype":"mp4","vwidth":854},"video_3":{"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vYWYwMDg2ZDE1MWI3NTdjMjU3MmQ2NGY1NzU2MjRlMjEvNThlZWY3MjMvdmlkZW8vbS8yMjAwNzQ5N2NlNDY5Nzg0MjgxOGIwZjdhNmFjY2U0ZWYyNzExNDQ4ZmYwMDAwMDFhMzhjN2Y2MWQ2Lw==","bitrate":527642,"definition":"720p","main_url":"aHR0cDovL3YzLjM2NXlnLmNvbS9hZjAwODZkMTUxYjc1N2MyNTcyZDY0ZjU3NTYyNGUyMS81OGVlZjcyMy92aWRlby9tLzIyMDA3NDk3Y2U0Njk3ODQyODE4YjBmN2E2YWNjZTRlZjI3MTE0NDhmZjAwMDAwMWEzOGM3ZjYxZDYv","preload_interval":25,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"size":2.08755626E8,"socket_buffer":3.165852E8,"user_video_proxy":1,"vheight":720,"vtype":"mp4","vwidth":1280}}}
     * message : success
     * code : 0
     * total : 3
     */

    private DataBean data;
    private String message;
    private int code;
    private int total;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class DataBean {
        /**
         * status : 10
         * user_id : toutiao
         * video_id : 65dee91dcc364dec9301028c6fc60715
         * big_thumbs : [{"img_num":99,"img_url":"https://p1.pstatp.com/origin/19c80005ce37e46715a5","img_x_size":160,"img_y_size":90,"img_x_len":1,"img_y_len":99}]
         * video_duration : 2806.92
         * video_list : {"video_1":{"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vNzJhYThlMTY0MjI4ZjY0MWQ0YWQzOTQ1ZGYzZTNmMmEvNThlZWY3MjMvdmlkZW8vbS8yMjBiMTI4MjMzMjMyNDQ0NzEyYWI3MzFkN2I3Njk4NmU1OTExNDQ4YWYwMDAwMjI3MTkwNGY2MDljLw==","bitrate":135315,"definition":"360p","main_url":"aHR0cDovL3Y2LjM2NXlnLmNvbS92aWRlby9tLzIyMGIxMjgyMzMyMzI0NDQ3MTJhYjczMWQ3Yjc2OTg2ZTU5MTE0NDhhZjAwMDAyMjcxOTA0ZjYwOWMvP0V4cGlyZXM9MTQ5MjA1OTQ0MyZBV1NBY2Nlc3NLZXlJZD1xaDBoOVRkY0VNb1Myb1BqN2FLWCZTaWduYXR1cmU9eHpjNTg3eGVtc2dBM0NHOVZkS21hN3FIRUhRJTNE","preload_interval":25,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"size":7.1097334E7,"socket_buffer":8.1189E7,"user_video_proxy":1,"vheight":360,"vtype":"mp4","vwidth":640},"video_2":{"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vMzJiZTg3ZDI1N2VjZmNiOWJjZThjN2ViMWFiNzA3ZWYvNThlZWY3MjMvdmlkZW8vbS8yMjA1ZDc1MmM0MmUyZTM0Njk3YjkzMjgxMWM1ZDllZGI0YjExNDRiYjIwMDAwMmIzYjg2MDM3NDhlLw==","bitrate":213144,"definition":"480p","main_url":"aHR0cDovL3YzLjM2NXlnLmNvbS8zMmJlODdkMjU3ZWNmY2I5YmNlOGM3ZWIxYWI3MDdlZi81OGVlZjcyMy92aWRlby9tLzIyMDVkNzUyYzQyZTJlMzQ2OTdiOTMyODExYzVkOWVkYjRiMTE0NGJiMjAwMDAyYjNiODYwMzc0OGUv","preload_interval":25,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"size":9.8414714E7,"socket_buffer":1.278864E8,"user_video_proxy":1,"vheight":480,"vtype":"mp4","vwidth":854},"video_3":{"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vYWYwMDg2ZDE1MWI3NTdjMjU3MmQ2NGY1NzU2MjRlMjEvNThlZWY3MjMvdmlkZW8vbS8yMjAwNzQ5N2NlNDY5Nzg0MjgxOGIwZjdhNmFjY2U0ZWYyNzExNDQ4ZmYwMDAwMDFhMzhjN2Y2MWQ2Lw==","bitrate":527642,"definition":"720p","main_url":"aHR0cDovL3YzLjM2NXlnLmNvbS9hZjAwODZkMTUxYjc1N2MyNTcyZDY0ZjU3NTYyNGUyMS81OGVlZjcyMy92aWRlby9tLzIyMDA3NDk3Y2U0Njk3ODQyODE4YjBmN2E2YWNjZTRlZjI3MTE0NDhmZjAwMDAwMWEzOGM3ZjYxZDYv","preload_interval":25,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"size":2.08755626E8,"socket_buffer":3.165852E8,"user_video_proxy":1,"vheight":720,"vtype":"mp4","vwidth":1280}}
         */

        private int status;
        private String user_id;
        private String video_id;
        private double video_duration;
        private VideoListBean video_list;
        private List<BigThumbsBean> big_thumbs;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public double getVideo_duration() {
            return video_duration;
        }

        public void setVideo_duration(double video_duration) {
            this.video_duration = video_duration;
        }

        public VideoListBean getVideo_list() {
            return video_list;
        }

        public void setVideo_list(VideoListBean video_list) {
            this.video_list = video_list;
        }

        public List<BigThumbsBean> getBig_thumbs() {
            return big_thumbs;
        }

        public void setBig_thumbs(List<BigThumbsBean> big_thumbs) {
            this.big_thumbs = big_thumbs;
        }

        public static class VideoListBean {
            /**
             * video_1 : {"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vNzJhYThlMTY0MjI4ZjY0MWQ0YWQzOTQ1ZGYzZTNmMmEvNThlZWY3MjMvdmlkZW8vbS8yMjBiMTI4MjMzMjMyNDQ0NzEyYWI3MzFkN2I3Njk4NmU1OTExNDQ4YWYwMDAwMjI3MTkwNGY2MDljLw==","bitrate":135315,"definition":"360p","main_url":"aHR0cDovL3Y2LjM2NXlnLmNvbS92aWRlby9tLzIyMGIxMjgyMzMyMzI0NDQ3MTJhYjczMWQ3Yjc2OTg2ZTU5MTE0NDhhZjAwMDAyMjcxOTA0ZjYwOWMvP0V4cGlyZXM9MTQ5MjA1OTQ0MyZBV1NBY2Nlc3NLZXlJZD1xaDBoOVRkY0VNb1Myb1BqN2FLWCZTaWduYXR1cmU9eHpjNTg3eGVtc2dBM0NHOVZkS21hN3FIRUhRJTNE","preload_interval":25,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"size":7.1097334E7,"socket_buffer":8.1189E7,"user_video_proxy":1,"vheight":360,"vtype":"mp4","vwidth":640}
             * video_2 : {"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vMzJiZTg3ZDI1N2VjZmNiOWJjZThjN2ViMWFiNzA3ZWYvNThlZWY3MjMvdmlkZW8vbS8yMjA1ZDc1MmM0MmUyZTM0Njk3YjkzMjgxMWM1ZDllZGI0YjExNDRiYjIwMDAwMmIzYjg2MDM3NDhlLw==","bitrate":213144,"definition":"480p","main_url":"aHR0cDovL3YzLjM2NXlnLmNvbS8zMmJlODdkMjU3ZWNmY2I5YmNlOGM3ZWIxYWI3MDdlZi81OGVlZjcyMy92aWRlby9tLzIyMDVkNzUyYzQyZTJlMzQ2OTdiOTMyODExYzVkOWVkYjRiMTE0NGJiMjAwMDAyYjNiODYwMzc0OGUv","preload_interval":25,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"size":9.8414714E7,"socket_buffer":1.278864E8,"user_video_proxy":1,"vheight":480,"vtype":"mp4","vwidth":854}
             * video_3 : {"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vYWYwMDg2ZDE1MWI3NTdjMjU3MmQ2NGY1NzU2MjRlMjEvNThlZWY3MjMvdmlkZW8vbS8yMjAwNzQ5N2NlNDY5Nzg0MjgxOGIwZjdhNmFjY2U0ZWYyNzExNDQ4ZmYwMDAwMDFhMzhjN2Y2MWQ2Lw==","bitrate":527642,"definition":"720p","main_url":"aHR0cDovL3YzLjM2NXlnLmNvbS9hZjAwODZkMTUxYjc1N2MyNTcyZDY0ZjU3NTYyNGUyMS81OGVlZjcyMy92aWRlby9tLzIyMDA3NDk3Y2U0Njk3ODQyODE4YjBmN2E2YWNjZTRlZjI3MTE0NDhmZjAwMDAwMWEzOGM3ZjYxZDYv","preload_interval":25,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"size":2.08755626E8,"socket_buffer":3.165852E8,"user_video_proxy":1,"vheight":720,"vtype":"mp4","vwidth":1280}
             */

            private Video1Bean video_1;
            private Video2Bean video_2;
            private Video3Bean video_3;

            public Video1Bean getVideo_1() {
                return video_1;
            }

            public void setVideo_1(Video1Bean video_1) {
                this.video_1 = video_1;
            }

            public Video2Bean getVideo_2() {
                return video_2;
            }

            public void setVideo_2(Video2Bean video_2) {
                this.video_2 = video_2;
            }

            public Video3Bean getVideo_3() {
                return video_3;
            }

            public void setVideo_3(Video3Bean video_3) {
                this.video_3 = video_3;
            }

            public static class Video1Bean {
                /**
                 * backup_url_1 : aHR0cDovL3Y3LnBzdGF0cC5jb20vNzJhYThlMTY0MjI4ZjY0MWQ0YWQzOTQ1ZGYzZTNmMmEvNThlZWY3MjMvdmlkZW8vbS8yMjBiMTI4MjMzMjMyNDQ0NzEyYWI3MzFkN2I3Njk4NmU1OTExNDQ4YWYwMDAwMjI3MTkwNGY2MDljLw==
                 * bitrate : 135315
                 * definition : 360p
                 * main_url : aHR0cDovL3Y2LjM2NXlnLmNvbS92aWRlby9tLzIyMGIxMjgyMzMyMzI0NDQ3MTJhYjczMWQ3Yjc2OTg2ZTU5MTE0NDhhZjAwMDAyMjcxOTA0ZjYwOWMvP0V4cGlyZXM9MTQ5MjA1OTQ0MyZBV1NBY2Nlc3NLZXlJZD1xaDBoOVRkY0VNb1Myb1BqN2FLWCZTaWduYXR1cmU9eHpjNTg3eGVtc2dBM0NHOVZkS21hN3FIRUhRJTNE
                 * preload_interval : 25
                 * preload_max_step : 10
                 * preload_min_step : 5
                 * preload_size : 327680
                 * size : 7.1097334E7
                 * socket_buffer : 8.1189E7
                 * user_video_proxy : 1
                 * vheight : 360
                 * vtype : mp4
                 * vwidth : 640
                 */

                private String backup_url_1;
                private int bitrate;
                private String definition;
                private String main_url;
                private int preload_interval;
                private int preload_max_step;
                private int preload_min_step;
                private int preload_size;
                private double size;
                private double socket_buffer;
                private int user_video_proxy;
                private int vheight;
                private String vtype;
                private int vwidth;

                public String getBackup_url_1() {
                    return backup_url_1;
                }

                public void setBackup_url_1(String backup_url_1) {
                    this.backup_url_1 = backup_url_1;
                }

                public int getBitrate() {
                    return bitrate;
                }

                public void setBitrate(int bitrate) {
                    this.bitrate = bitrate;
                }

                public String getDefinition() {
                    return definition;
                }

                public void setDefinition(String definition) {
                    this.definition = definition;
                }

                public String getMain_url() {
                    return main_url;
                }

                public void setMain_url(String main_url) {
                    this.main_url = main_url;
                }

                public int getPreload_interval() {
                    return preload_interval;
                }

                public void setPreload_interval(int preload_interval) {
                    this.preload_interval = preload_interval;
                }

                public int getPreload_max_step() {
                    return preload_max_step;
                }

                public void setPreload_max_step(int preload_max_step) {
                    this.preload_max_step = preload_max_step;
                }

                public int getPreload_min_step() {
                    return preload_min_step;
                }

                public void setPreload_min_step(int preload_min_step) {
                    this.preload_min_step = preload_min_step;
                }

                public int getPreload_size() {
                    return preload_size;
                }

                public void setPreload_size(int preload_size) {
                    this.preload_size = preload_size;
                }

                public double getSize() {
                    return size;
                }

                public void setSize(double size) {
                    this.size = size;
                }

                public double getSocket_buffer() {
                    return socket_buffer;
                }

                public void setSocket_buffer(double socket_buffer) {
                    this.socket_buffer = socket_buffer;
                }

                public int getUser_video_proxy() {
                    return user_video_proxy;
                }

                public void setUser_video_proxy(int user_video_proxy) {
                    this.user_video_proxy = user_video_proxy;
                }

                public int getVheight() {
                    return vheight;
                }

                public void setVheight(int vheight) {
                    this.vheight = vheight;
                }

                public String getVtype() {
                    return vtype;
                }

                public void setVtype(String vtype) {
                    this.vtype = vtype;
                }

                public int getVwidth() {
                    return vwidth;
                }

                public void setVwidth(int vwidth) {
                    this.vwidth = vwidth;
                }
            }

            public static class Video2Bean {
                /**
                 * backup_url_1 : aHR0cDovL3Y3LnBzdGF0cC5jb20vMzJiZTg3ZDI1N2VjZmNiOWJjZThjN2ViMWFiNzA3ZWYvNThlZWY3MjMvdmlkZW8vbS8yMjA1ZDc1MmM0MmUyZTM0Njk3YjkzMjgxMWM1ZDllZGI0YjExNDRiYjIwMDAwMmIzYjg2MDM3NDhlLw==
                 * bitrate : 213144
                 * definition : 480p
                 * main_url : aHR0cDovL3YzLjM2NXlnLmNvbS8zMmJlODdkMjU3ZWNmY2I5YmNlOGM3ZWIxYWI3MDdlZi81OGVlZjcyMy92aWRlby9tLzIyMDVkNzUyYzQyZTJlMzQ2OTdiOTMyODExYzVkOWVkYjRiMTE0NGJiMjAwMDAyYjNiODYwMzc0OGUv
                 * preload_interval : 25
                 * preload_max_step : 10
                 * preload_min_step : 5
                 * preload_size : 327680
                 * size : 9.8414714E7
                 * socket_buffer : 1.278864E8
                 * user_video_proxy : 1
                 * vheight : 480
                 * vtype : mp4
                 * vwidth : 854
                 */

                private String backup_url_1;
                private int bitrate;
                private String definition;
                private String main_url;
                private int preload_interval;
                private int preload_max_step;
                private int preload_min_step;
                private int preload_size;
                private double size;
                private double socket_buffer;
                private int user_video_proxy;
                private int vheight;
                private String vtype;
                private int vwidth;

                public String getBackup_url_1() {
                    return backup_url_1;
                }

                public void setBackup_url_1(String backup_url_1) {
                    this.backup_url_1 = backup_url_1;
                }

                public int getBitrate() {
                    return bitrate;
                }

                public void setBitrate(int bitrate) {
                    this.bitrate = bitrate;
                }

                public String getDefinition() {
                    return definition;
                }

                public void setDefinition(String definition) {
                    this.definition = definition;
                }

                public String getMain_url() {
                    return main_url;
                }

                public void setMain_url(String main_url) {
                    this.main_url = main_url;
                }

                public int getPreload_interval() {
                    return preload_interval;
                }

                public void setPreload_interval(int preload_interval) {
                    this.preload_interval = preload_interval;
                }

                public int getPreload_max_step() {
                    return preload_max_step;
                }

                public void setPreload_max_step(int preload_max_step) {
                    this.preload_max_step = preload_max_step;
                }

                public int getPreload_min_step() {
                    return preload_min_step;
                }

                public void setPreload_min_step(int preload_min_step) {
                    this.preload_min_step = preload_min_step;
                }

                public int getPreload_size() {
                    return preload_size;
                }

                public void setPreload_size(int preload_size) {
                    this.preload_size = preload_size;
                }

                public double getSize() {
                    return size;
                }

                public void setSize(double size) {
                    this.size = size;
                }

                public double getSocket_buffer() {
                    return socket_buffer;
                }

                public void setSocket_buffer(double socket_buffer) {
                    this.socket_buffer = socket_buffer;
                }

                public int getUser_video_proxy() {
                    return user_video_proxy;
                }

                public void setUser_video_proxy(int user_video_proxy) {
                    this.user_video_proxy = user_video_proxy;
                }

                public int getVheight() {
                    return vheight;
                }

                public void setVheight(int vheight) {
                    this.vheight = vheight;
                }

                public String getVtype() {
                    return vtype;
                }

                public void setVtype(String vtype) {
                    this.vtype = vtype;
                }

                public int getVwidth() {
                    return vwidth;
                }

                public void setVwidth(int vwidth) {
                    this.vwidth = vwidth;
                }
            }

            public static class Video3Bean {
                /**
                 * backup_url_1 : aHR0cDovL3Y3LnBzdGF0cC5jb20vYWYwMDg2ZDE1MWI3NTdjMjU3MmQ2NGY1NzU2MjRlMjEvNThlZWY3MjMvdmlkZW8vbS8yMjAwNzQ5N2NlNDY5Nzg0MjgxOGIwZjdhNmFjY2U0ZWYyNzExNDQ4ZmYwMDAwMDFhMzhjN2Y2MWQ2Lw==
                 * bitrate : 527642
                 * definition : 720p
                 * main_url : aHR0cDovL3YzLjM2NXlnLmNvbS9hZjAwODZkMTUxYjc1N2MyNTcyZDY0ZjU3NTYyNGUyMS81OGVlZjcyMy92aWRlby9tLzIyMDA3NDk3Y2U0Njk3ODQyODE4YjBmN2E2YWNjZTRlZjI3MTE0NDhmZjAwMDAwMWEzOGM3ZjYxZDYv
                 * preload_interval : 25
                 * preload_max_step : 10
                 * preload_min_step : 5
                 * preload_size : 327680
                 * size : 2.08755626E8
                 * socket_buffer : 3.165852E8
                 * user_video_proxy : 1
                 * vheight : 720
                 * vtype : mp4
                 * vwidth : 1280
                 */

                private String backup_url_1;
                private int bitrate;
                private String definition;
                private String main_url;
                private int preload_interval;
                private int preload_max_step;
                private int preload_min_step;
                private int preload_size;
                private double size;
                private double socket_buffer;
                private int user_video_proxy;
                private int vheight;
                private String vtype;
                private int vwidth;

                public String getBackup_url_1() {
                    return backup_url_1;
                }

                public void setBackup_url_1(String backup_url_1) {
                    this.backup_url_1 = backup_url_1;
                }

                public int getBitrate() {
                    return bitrate;
                }

                public void setBitrate(int bitrate) {
                    this.bitrate = bitrate;
                }

                public String getDefinition() {
                    return definition;
                }

                public void setDefinition(String definition) {
                    this.definition = definition;
                }

                public String getMain_url() {
                    return main_url;
                }

                public void setMain_url(String main_url) {
                    this.main_url = main_url;
                }

                public int getPreload_interval() {
                    return preload_interval;
                }

                public void setPreload_interval(int preload_interval) {
                    this.preload_interval = preload_interval;
                }

                public int getPreload_max_step() {
                    return preload_max_step;
                }

                public void setPreload_max_step(int preload_max_step) {
                    this.preload_max_step = preload_max_step;
                }

                public int getPreload_min_step() {
                    return preload_min_step;
                }

                public void setPreload_min_step(int preload_min_step) {
                    this.preload_min_step = preload_min_step;
                }

                public int getPreload_size() {
                    return preload_size;
                }

                public void setPreload_size(int preload_size) {
                    this.preload_size = preload_size;
                }

                public double getSize() {
                    return size;
                }

                public void setSize(double size) {
                    this.size = size;
                }

                public double getSocket_buffer() {
                    return socket_buffer;
                }

                public void setSocket_buffer(double socket_buffer) {
                    this.socket_buffer = socket_buffer;
                }

                public int getUser_video_proxy() {
                    return user_video_proxy;
                }

                public void setUser_video_proxy(int user_video_proxy) {
                    this.user_video_proxy = user_video_proxy;
                }

                public int getVheight() {
                    return vheight;
                }

                public void setVheight(int vheight) {
                    this.vheight = vheight;
                }

                public String getVtype() {
                    return vtype;
                }

                public void setVtype(String vtype) {
                    this.vtype = vtype;
                }

                public int getVwidth() {
                    return vwidth;
                }

                public void setVwidth(int vwidth) {
                    this.vwidth = vwidth;
                }
            }
        }

        public static class BigThumbsBean {
            /**
             * img_num : 99
             * img_url : https://p1.pstatp.com/origin/19c80005ce37e46715a5
             * img_x_size : 160
             * img_y_size : 90
             * img_x_len : 1
             * img_y_len : 99
             */

            private int img_num;
            private String img_url;
            private int img_x_size;
            private int img_y_size;
            private int img_x_len;
            private int img_y_len;

            public int getImg_num() {
                return img_num;
            }

            public void setImg_num(int img_num) {
                this.img_num = img_num;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public int getImg_x_size() {
                return img_x_size;
            }

            public void setImg_x_size(int img_x_size) {
                this.img_x_size = img_x_size;
            }

            public int getImg_y_size() {
                return img_y_size;
            }

            public void setImg_y_size(int img_y_size) {
                this.img_y_size = img_y_size;
            }

            public int getImg_x_len() {
                return img_x_len;
            }

            public void setImg_x_len(int img_x_len) {
                this.img_x_len = img_x_len;
            }

            public int getImg_y_len() {
                return img_y_len;
            }

            public void setImg_y_len(int img_y_len) {
                this.img_y_len = img_y_len;
            }
        }
    }
}

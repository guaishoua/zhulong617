package com.hdj.data;

public class AdvertBean {
    /**
     * errNo : 0
     * result : {"ad_id":"12591","place_name":"APP_QD_01","height":"750","type":"2","info_url":"http://newoss.zhulong.com/ad/201912/02/1/100301rkdgcc9uho3bepgf.jpg","jump_url":"https://edu.zhulong.com/lesson/9214-1.html?f=app_qdt_sg_0","title":"16G平法图集","start_time":"1590336000","end_time":"1590940800","width":"750","prof":"sg","wb":"0"}
     */

    private int errNo;
    private ResultBean result;

    public int getErrNo() {
        return errNo;
    }

    public void setErrNo(int errNo) {
        this.errNo = errNo;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * ad_id : 12591
         * place_name : APP_QD_01
         * height : 750
         * type : 2
         * info_url : http://newoss.zhulong.com/ad/201912/02/1/100301rkdgcc9uho3bepgf.jpg
         * jump_url : https://edu.zhulong.com/lesson/9214-1.html?f=app_qdt_sg_0
         * title : 16G平法图集
         * start_time : 1590336000
         * end_time : 1590940800
         * width : 750
         * prof : sg
         * wb : 0
         */

        private String ad_id;
        private String place_name;
        private String height;
        private String type;
        private String info_url;
        private String jump_url;
        private String title;
        private String start_time;
        private String end_time;
        private String width;
        private String prof;
        private String wb;

        public String getAd_id() {
            return ad_id;
        }

        public void setAd_id(String ad_id) {
            this.ad_id = ad_id;
        }

        public String getPlace_name() {
            return place_name;
        }

        public void setPlace_name(String place_name) {
            this.place_name = place_name;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getInfo_url() {
            return info_url;
        }

        public void setInfo_url(String info_url) {
            this.info_url = info_url;
        }

        public String getJump_url() {
            return jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getProf() {
            return prof;
        }

        public void setProf(String prof) {
            this.prof = prof;
        }

        public String getWb() {
            return wb;
        }

        public void setWb(String wb) {
            this.wb = wb;
        }
    }
}

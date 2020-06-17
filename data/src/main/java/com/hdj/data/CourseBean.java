package com.hdj.data;

import java.util.List;

public class CourseBean {
    private List<ListsBean> lists;

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean {
        /**
         * id : 26831
         * lesson_id : 4915
         * lesson_name : 建筑施工图设计实操训练营
         * type : 1
         * price : 1999.00
         * vip_price : 1799.10
         * show_vip_tag : 9
         * thumb : https://newoss.zhulong.com/edu/202001/17/58/225958oc1ou3ph7afzefso.jpg
         * specialty_id : 1
         * studentnum : 2147
         * m_specialty_id : 1
         * rank : 4.9
         * comment_html : ["full_star","full_star","full_star","full_star","half_star"]
         * comment_htmls :
         * rate : 97%
         * vip_tag_status : 0
         */

        private String id;
        private String lesson_id;
        private String lesson_name;
        private String type;
        private String price;
        private String vip_price;
        private int show_vip_tag;
        private String thumb;
        private String specialty_id;
        private String studentnum;
        private String m_specialty_id;
        private String rank;
        private String comment_htmls;
        private String rate;
        private int vip_tag_status;
        private List<String> comment_html;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLesson_id() {
            return lesson_id;
        }

        public void setLesson_id(String lesson_id) {
            this.lesson_id = lesson_id;
        }

        public String getLesson_name() {
            return lesson_name;
        }

        public void setLesson_name(String lesson_name) {
            this.lesson_name = lesson_name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getVip_price() {
            return vip_price;
        }

        public void setVip_price(String vip_price) {
            this.vip_price = vip_price;
        }

        public int getShow_vip_tag() {
            return show_vip_tag;
        }

        public void setShow_vip_tag(int show_vip_tag) {
            this.show_vip_tag = show_vip_tag;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getSpecialty_id() {
            return specialty_id;
        }

        public void setSpecialty_id(String specialty_id) {
            this.specialty_id = specialty_id;
        }

        public String getStudentnum() {
            return studentnum;
        }

        public void setStudentnum(String studentnum) {
            this.studentnum = studentnum;
        }

        public String getM_specialty_id() {
            return m_specialty_id;
        }

        public void setM_specialty_id(String m_specialty_id) {
            this.m_specialty_id = m_specialty_id;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getComment_htmls() {
            return comment_htmls;
        }

        public void setComment_htmls(String comment_htmls) {
            this.comment_htmls = comment_htmls;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public int getVip_tag_status() {
            return vip_tag_status;
        }

        public void setVip_tag_status(int vip_tag_status) {
            this.vip_tag_status = vip_tag_status;
        }

        public List<String> getComment_html() {
            return comment_html;
        }

        public void setComment_html(List<String> comment_html) {
            this.comment_html = comment_html;
        }

    }
}

package com.robbin.rong.account.domain;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/1.
 */
public class Artical {
    public String showapi_res_code;
    public String showapi_res_error;
    public Body showapi_res_body;

    public class Body {
        public String ret_code;
        public PageBean pagebean;
    }

    public class PageBean {
        public String allNum;
        public String allPages;
        public ArrayList<Content> contentlist;
        public String currentPage;
        public String maxResult;

    }

    public class Content {
        public String contentImg;
        public String date;
        public String id;
        public String title;
        public String typeId;
        public String typeName;
        public String url;
        public String userLogo;
        public String userLogo_code;
        public String userName;
    }
}

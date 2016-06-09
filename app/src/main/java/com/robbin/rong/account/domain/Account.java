package com.robbin.rong.account.domain;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/3.
 */
public class Account {
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
        public String code2img;
        public String id;
        public String pubNum;
        public String tag;
        public String type1_id;
        public String type1_name;
        public String type2_id;
        public String type2_name;
        public String userLogo;
        public String weiNum;
    }
}

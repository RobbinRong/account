package com.robbin.rong.account.domain;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/31.
 */
public class ArticalCategory {
    public String showapi_res_code;
    public String showapi_res_error;
    public  Body showapi_res_body;

    @Override
    public String toString() {
        return "ArticalCategory{" +
                "showapi_res_code='" + showapi_res_code + '\'' +
                ", showapi_res_error='" + showapi_res_error + '\'' +
                ", showapi_res_body='" + showapi_res_body + '\'' +
                '}';
    }

    public class  Body {

            public String ret_code;
            public ArrayList<Type> typeList;

        @Override
        public String toString() {
            return "Body{" +
                    "ret_code='" + ret_code + '\'' +
                    ", typeList='" + typeList + '\'' +
                    '}';
        }
    }
    public  class  Type{

        public  String id;
        public String name;

        @Override
        public String toString() {
            return "Type{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}

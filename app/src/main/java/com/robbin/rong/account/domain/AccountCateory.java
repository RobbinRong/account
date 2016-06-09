package com.robbin.rong.account.domain;

import java.util.ArrayList;



public class AccountCateory {

	public int showapi_res_code;
	public String showapi_res_error;
	public NewsMenuData showapi_res_body;

	// 侧边栏数据对象
	public class NewsMenuData {
		public ArrayList<Alt> allList;

		@Override
		public String toString() {
			return "NewsMenuData{" +
					"allList=" + allList +
					'}';
		}
	}
	public  class Alt  extends BaseCategory{//一级分类
		public ArrayList<Child> childList;

		@Override
		public String toString() {
			return "Alt{" +
					"id='" + id + '\'' +
					", name='" + name + '\'' +
					", childList=" + childList +
					'}';
		}
	}

	public  class Child {//二级分类
		public String id;
		public String name;

		@Override
		public String toString() {
			return "Child{" +
					"id='" + id + '\'' +
					", name='" + name + '\'' +
					'}';
		}
	}

	@Override
	public String toString() {
		return "AccountCateory{" +
				"showapi_res_code=" + showapi_res_code +
				", showapi_res_error='" + showapi_res_error + '\'' +
				", showapi_res_body=" + showapi_res_body +
				'}';
	}
}

package com.robbin.rong.account.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/4/2.
 */
public class PrefUtil {
    private static  final String PREF_NAME="config";
    public static  boolean getBoolean(Context context,String key,boolean defultValue){

        SharedPreferences sp=context.getSharedPreferences(PREF_NAME,context.MODE_PRIVATE);
        return  sp.getBoolean(key,defultValue);
    }
    public static  void setBoolean(Context context,String key,boolean value){

        SharedPreferences sp=context.getSharedPreferences(PREF_NAME,context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void setString(Context ctx, String key, String value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
}

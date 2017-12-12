package saiwei.com.river.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.LinkedHashSet;
import java.util.Set;

import saiwei.com.river.MyApp;

/**
 * Created by yuwei.song on 2015/7/8.
 */


public class SharePreferenceUtil {
//    public static final String ACTIVE_POSSIVE = "ACTIVE_POSSIVE";
    public static final String PASSIVE_LISTS_MOBILE_NETWORK = "PASSIVE_LISTS_MOBILE_NETWORK";

    private static final String BANNER_LIST = "BANNER_LIST";

    public static final String CAR_PARKING = "hechang";


    /**
     * 查询上一次巡河记录
     *
     * 如果是空，就重新一条新的记录
     * 如果不为空，就继续上一巡河记录
     */
    public static final String SHARE_PREFERENCE_LASTXUNHE = "last_xunhe";

    public static final String SHARE_PREFERENCE_LASTXUNHE_RECORD = "last_xunhe_record";

    public static final String SHARE_PREFERENCE_USERNAME = "username";
    public static final String SHARE_PREFERENCE_PASSWORD = "password";

    public static final String SHARE_PREFERENCE_UNCOMPLETECOUNTS = "uncompleteCounts";
    public static final String SHARE_PREFERENCE_CRUISECOUNTS = "cruiseCounts";


    public static final String SHARE_PREFERENCE_TOWN = "town";
    public static final String SHARE_PREFERENCE_TOWNCODE = "towncode";
    public static final String SHARE_PREFERENCE_COUNTYCODE = "countycode";
    public static final String SHARE_PREFERENCE_MOBIEL = "mobile";

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static SharePreferenceUtil instance;

    public static synchronized SharePreferenceUtil getInstance() {
        if(instance == null) {
            instance = new SharePreferenceUtil();
        }
        return instance;
    }

    private SharePreferenceUtil() {
        this.sp = MyApp.getApp().getSharedPreferences(CAR_PARKING, Context.MODE_PRIVATE);
        this.editor = sp.edit();
    }

//    public Set<String> getStr(String key){
//        return sp.getStringSet(key, new LinkedHashSet<String>());
//    }

//    public void update(String key, Set<String> sets){
//        this.editor.putStringSet(key, sets);
//        this.editor.apply();
//    }
//
//    public void commit(String key, Set<String> sets){
//        this.editor.putStringSet(key, sets);
//        this.editor.commit();
//    }

    public String getStr(String key){
        return sp.getString(key,"");
    }


    public void putStr(String key, String value){
        this.editor.putString(key,value);
        this.editor.commit();
    }

    public void putInt(String key, int value){
        this.editor.putInt(key,value);
        this.editor.commit();
    }

    public int getInt(String key){
        return sp.getInt(key,0);
    }


}

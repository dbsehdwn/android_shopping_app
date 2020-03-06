package com.dinosaurfactory.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    static final String USER_NUM = "usernum";
    static final String USER_NAME = "username";
    static final String USER_PW = "password";
    static final String USER_id = "userid";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setUser(Context ctx, String userName,String pw, String id, int num) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_NAME, userName);
        editor.putString(USER_id, id);
        editor.putString(USER_PW, pw);
        editor.putInt(USER_NUM, num);
        editor.commit();
    }

    // 저장된 정보 가져오기
    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(USER_NAME, "");
    }
    public static String getUserid(Context ctx) {
        return getSharedPreferences(ctx).getString(USER_id, "");
    }
    public static String getUserpw(Context ctx) {
        return getSharedPreferences(ctx).getString(USER_PW, "");
    }
    public static int getUsernum(Context ctx) {
        return getSharedPreferences(ctx).getInt(USER_NUM, 0);
    }

    // 로그아웃
    public static void clearUser(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}

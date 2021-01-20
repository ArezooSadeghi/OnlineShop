package com.example.onlineshop.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static final String EMAIL = "email";
    private static final String IS_LOGIN = "isLogin";

    public static String getEmail(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getString(EMAIL, null);
    }

    public static void setEmail(Context context, String email) {
        SharedPreferences preferences = getSharedPreferences(context);
        preferences.edit().putString(EMAIL, email).commit();
    }

    public static boolean getIsLogin(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public static void setIsLogin(Context context, boolean isLogin) {
        SharedPreferences preferences = getSharedPreferences(context);
        preferences.edit().putBoolean(IS_LOGIN, isLogin).commit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(
                context.getPackageName(),
                context.MODE_PRIVATE);
    }

}

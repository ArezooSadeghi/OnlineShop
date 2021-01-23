package com.example.onlineshop.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static final String EMAIL = "email";
    private static final String IS_LOGIN = "isLogin";
    private static final String LAST_ID = "lastId";
    private static final String IS_ON_NOTIFICATION = "isOnNotification";
    private static final String SELECTED_BUTTON_ID = "selectedButtonId";

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

    public static int getLastId(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getInt(LAST_ID, 0);
    }

    public static void setLastId(Context context, int lastId) {
        SharedPreferences preferences = getSharedPreferences(context);
        preferences.edit().putInt(LAST_ID, lastId).commit();
    }

    public static boolean getIsOnNotification(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getBoolean(IS_ON_NOTIFICATION, false);
    }

    public static void setIsOnNotification(Context context, boolean isOnNotification) {
        SharedPreferences preferences = getSharedPreferences(context);
        preferences.edit().putBoolean(IS_ON_NOTIFICATION, isOnNotification).commit();
    }

    public static int getSelectedButtonId(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getInt(SELECTED_BUTTON_ID, 0);
    }

    public static void setSelectedButtonId(Context context, int selectedButtonId) {
        SharedPreferences preferences = getSharedPreferences(context);
        preferences.edit().putInt(SELECTED_BUTTON_ID, selectedButtonId).commit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(
                context.getPackageName(),
                context.MODE_PRIVATE);
    }
}

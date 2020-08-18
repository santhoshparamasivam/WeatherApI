package com.example.weatherapplication.sessionmanager;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager {

    private SharedPreferences  pref;
    private SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "FirebaseMessagae";

    public static final String Name = "nameKey";
    public static final String Photo = "photoKey";
    public static final String Email = "emailKey";

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setSessionValue(String key, Object value) {
        SharedPreferences.Editor editor = pref.edit();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else {
            editor.putString(key, (String) value);
        }
        editor.apply();
    }
    public String getStringKey(String key) {
        return  pref.getString(key, "");
    }
    public void logoutUser() {

        editor.clear();
        editor.apply();
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();

    }
}

package com.kiran.weighttracker;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public Session(Context context) {
        sharedPreferences = context.getSharedPreferences("", Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        this.editor.commit();
    }

    public void setStringValue(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }
    public String getStringValue(String key){
        return sharedPreferences.getString(key,"");
    }

    public void setBooleanValue(String key, Boolean value){
        editor.putBoolean(key, value);
        editor.commit();
    }
}

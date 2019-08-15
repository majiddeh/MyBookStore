package com.example.mybookstore.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPrefrences {
    private static final String USER_SHARED_PREF_NAME = "user_shared_pref";
    private SharedPreferences sharedPreferences;

    public UserSharedPrefrences(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserLoginInfo(String phone){
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(Put.phone,phone);
        editor.apply();
    }

    public void exitFromAccount(){
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(Put.phone,"ورود/عضویت");
        editor.apply();
    }

    public String getUserLoginInfo(){
        return sharedPreferences.getString(Put.phone,"ورود/عضویت");
    }

}

package com.example.mybookstore.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPrefrences {
    private static final String USER_SHARED_PREF_NAME = "user_shared_pref";
    private SharedPreferences sharedPreferences;

    public UserSharedPrefrences(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserLoginInfo(String phone,String image){
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(Put.phone,phone);
        editor.putString(Put.image,image);
        editor.apply();
    }

    public void exitFromAccount(){
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(Put.phone,"ورود/عضویت");
        editor.putString(Put.image,"");
        editor.apply();
    }

    public String getUserPhone(){
        return sharedPreferences.getString(Put.phone,"ورود/عضویت");
    }
    public String getUserImaje(){
        return sharedPreferences.getString(Put.image,"");
    }

}

package com.example.mybookstore.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPrefrences {
    private static final String USER_SHARED_PREF_NAME = "user_shared_pref";
    private SharedPreferences sharedPreferences;

    public UserSharedPrefrences(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserLoginInfo(String username,String image,String token){
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(Put.username,username);
        editor.putString(Put.token,token);
        editor.putString(Put.image,image);
        editor.apply();
    }
    public void saveUserLoginInfoHA(String username ,String token){
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(Put.username,username);
        editor.putString(Put.token,token);
        editor.apply();
    }

//    public void saveUserLoginInfo(String username,String image){
//        SharedPreferences.Editor editor =sharedPreferences.edit();
//        editor.putString(Put.username,username);
//        editor.putString(Put.image,image);
//        editor.apply();
//    }

    public void exitFromAccount(){
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(Put.username,"ورود/عضویت");
        editor.putString(Put.image,"");
        editor.apply();
    }

    public String getUserName(){
        return sharedPreferences.getString(Put.username,"ورود/عضویت");
    }
    public String getUserImaje(){
        return sharedPreferences.getString(Put.image,"");
    }

}

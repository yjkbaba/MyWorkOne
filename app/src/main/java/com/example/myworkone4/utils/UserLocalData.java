package com.example.myworkone4.utils;

import android.content.Context;
import android.text.TextUtils;

import com.example.myworkone4.Contants;
import com.example.myworkone4.bean.User;


public class UserLocalData {

    public static void putUser(Context context, User user){//保存和取出用户信息，还有token
        String user_json =  JSONUtil.toJSON(user);
        PreferencesUtils.putString(context, Contants.USER_JSON,user_json);
    }

    public static void putToken(Context context,String token){
        PreferencesUtils.putString(context, Contants.TOKEN,token);
    }

    public static User getUser(Context context){
        String user_json= PreferencesUtils.getString(context,Contants.USER_JSON);
        if(!TextUtils.isEmpty(user_json)){
            return  JSONUtil.fromJson(user_json,User.class);
        }
        return  null;
    }

    public static  String getToken(Context context){
        return  PreferencesUtils.getString( context,Contants.TOKEN);
    }

    public static void clearUser(Context context){//退出登录时清除用户信息
        PreferencesUtils.putString(context, Contants.USER_JSON,"");
    }

    public static void clearToken(Context context){
        PreferencesUtils.putString(context, Contants.TOKEN,"");
    }
}

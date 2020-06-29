package com.example.myworkone4;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.myworkone4.bean.User;

import com.example.myworkone4.utils.UserLocalData;
import com.facebook.drawee.backends.pipeline.Fresco;

public class CniaoApplication extends Application {
    private User user;

    private static  CniaoApplication mInstance;
    public static  CniaoApplication getInstance(){
        return  mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initUser();//得到用户信息
        Fresco.initialize(this);//初始化
    }

    private void initUser(){
        this.user = UserLocalData.getUser(this);
    }

    public User getUser(){
        return user;
    }

    public void putUser(User user,String token){
        this.user = user;
        UserLocalData.putUser(this,user);
        UserLocalData.putToken(this,token);
    }

    public void clearUser(){
        this.user =null;
        UserLocalData.clearUser(this);
        UserLocalData.clearToken(this);

    }


    public String getToken(){

        return  UserLocalData.getToken(this);
    }



    private Intent intent;
    public void putIntent(Intent intent){
        this.intent = intent;
    }

    public Intent getIntent() {
        return this.intent;
    }

    public void jumpToTargetActivity(Context context){

        context.startActivity(intent);
        this.intent =null;
    }

}

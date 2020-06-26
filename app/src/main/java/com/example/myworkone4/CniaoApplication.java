package com.example.myworkone4;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class CniaoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
    }
}

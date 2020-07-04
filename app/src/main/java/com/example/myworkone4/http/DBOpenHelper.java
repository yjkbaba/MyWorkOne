package com.example.myworkone4.http;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myworkone4.bean.User;


import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    public DBOpenHelper(Context context){
        super(context,"db_test",null,2);
        db = getReadableDatabase();//可读状态
        db = getWritableDatabase();//可写状态
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table user(_id integer primary key autoincrement,phone varchar(20),password varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public void add(String phone,String password){//添加
        db.execSQL("INSERT INTO user (phone,password) VALUES(?,?)",new Object[]{phone,password});
    }
    public void delete(String phone,String password){//删除
        db.execSQL("DELETE FROM user WHERE phone = AND password ="+phone+password);
    }
    public void update(String password){//更新
        db.execSQL("UPDATE user SET password = ?",new Object[]{password});
    }

    public ArrayList<User> getAllData(){

        ArrayList<User> list = new ArrayList<User>();
        Cursor cursor = db.query("user",null,null,null,null,null,"phone DESC");
        while(cursor.moveToNext()){
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            list.add(new User(phone,password));
        }
        return list;
    }
}


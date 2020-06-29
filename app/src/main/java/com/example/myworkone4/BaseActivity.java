package com.example.myworkone4;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myworkone4.bean.User;

public class BaseActivity extends AppCompatActivity {



    public void startActivity(Intent intent,boolean isNeedLogin){


        if(isNeedLogin){

            User user =CniaoApplication.getInstance().getUser();
            if(user !=null){
                super.startActivity(intent);
            }
            else{

                CniaoApplication.getInstance().putIntent(intent);
                Intent loginIntent = new Intent(this
                        , LoginActivity.class);
                super.startActivity(intent);

            }

        }
        else{
            super.startActivity(intent);
        }

    }
}

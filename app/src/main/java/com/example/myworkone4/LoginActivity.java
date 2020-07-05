package com.example.myworkone4;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myworkone4.bean.User;
import com.example.myworkone4.fragment.MineFragment;
import com.example.myworkone4.http.DBOpenHelper;
import com.example.myworkone4.weiget.ClearEditText;
import com.example.myworkone4.weiget.CnToolbar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity{

   private DBOpenHelper mDBOpenHelper;

   @ViewInject(R.id.toolbar)
    private CnToolbar mToolBar;

   @ViewInject(R.id.text_phone)
   private ClearEditText mTextPhone;

   @ViewInject(R.id.text_pwd)
    private ClearEditText mTextPwd;


    //private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);//设置视图内容的配置文件
        setContentView(R.layout.activity_login);

        EditText mTextPhone1=(EditText)findViewById(R.id.text_phone);
        EditText mTextPwd1=(EditText)findViewById(R.id.text_pwd);

        Drawable phone=ContextCompat.getDrawable(this,R.drawable.icon_telphone_32);//调整 我的电话 图片大小
        phone.setBounds(0,0,180,180);
        mTextPhone1.setCompoundDrawables(phone,null,null,null);

        Drawable password=ContextCompat.getDrawable(this,R.drawable.icon_lock);//调整 我的密码 图片大小
        password.setBounds(0,0,180,180);
        mTextPwd1.setCompoundDrawables(password,null,null,null);


        ViewUtils.inject(this);
        initToolBar();

        mDBOpenHelper = new DBOpenHelper(getApplicationContext());



    }

    private void initToolBar(){
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LoginActivity.this.finish();

            }
        });
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.btn_login)//输入手机号码和密码进行登录
    public void login(View view){

        String phone = mTextPhone.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            ToastUtils.show(this, "请输入手机号码");
            return;
        }
        String pwd = mTextPwd.getText().toString().trim();
        if(TextUtils.isEmpty(pwd)){
            ToastUtils.show(this,"请输入密码");
            return;
        }

        Map<String,String> params = new HashMap<>(2);//电话和密码
        params.put("phone",phone);
        params.put("password", DESUtil.encode(Contants.DES_KEY,pwd));//加密

        okHttpHelper.post(Contants.API.LOGIN, params, new SpotsCallBack<LoginRespMsg<User>>(this) {

            @Override
            public void onSuccess(okhttp3.Response response, LoginRespMsg<User> userLoginRespMsg) {//登录成功后把user信息和token验证码保存到本地
                CniaoApplication application =  CniaoApplication.getInstance();
                application.putUser(userLoginRespMsg.getData(), userLoginRespMsg.getToken());
                if(application.getIntent() == null){
                    setResult(RESULT_OK);//返回码
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    application.jumpToTargetActivity(LoginActivity.this);
                    finish();
                }
            }

            @Override
            public void onError(okhttp3.Response response, int code, Exception e) {
            }
        });
    }

    @OnClick(R.id.txt_toReg)
    public void res(View view){//点击注册，跳转到注册界面

        Intent intent = new Intent(this, RegActivity.class);
        startActivity(intent);

    }*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.txt_toReg)
    public void reg(View view){//点击注册，跳转到注册界面

        Intent intent = new Intent(this, RegActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_login)//输入手机号码和密码进行登录
    public void login(View view){

        String phone = mTextPhone.getText().toString().trim();
        String password = mTextPwd.getText().toString().trim();
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
            ArrayList<User> data = mDBOpenHelper.getAllData();
            boolean match = false;
            for (int i = 0; i < data.size(); i++) {
                User user = data.get(i);
                if (phone.equals(user.getPhone()) && password.equals(user.getPassword())) {
                    match = true;
                    break;
                } else {
                    match = false;
                }
            }
            if (match) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MineFragment.class);
                setResult(RESULT_OK);//返回码
                finish();//销毁此Activity
            } else {
                Toast.makeText(this, "手机号码或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请输入你的手机号码或密码", Toast.LENGTH_SHORT).show();
        }
}

}

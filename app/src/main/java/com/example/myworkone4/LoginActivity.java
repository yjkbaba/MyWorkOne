package com.example.myworkone4;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myworkone4.bean.User;
import com.example.myworkone4.http.OkHttpHelper;
import com.example.myworkone4.http.SpotsCallBack;
import com.example.myworkone4.msg.LoginRespMsg;
import com.example.myworkone4.utils.DESUtil;
import com.example.myworkone4.utils.ToastUtils;
import com.example.myworkone4.weiget.ClearEditText;
import com.example.myworkone4.weiget.CnToolbar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;




public class LoginActivity extends AppCompatActivity{

    @ViewInject(R.id.toolbar)
    private CnToolbar mToolBar;

   @ViewInject(R.id.text_phone)
   private ClearEditText mTextPhone;

   @ViewInject(R.id.text_pwd)
    private ClearEditText mTextPwd;




    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText mTextPhone1=(EditText)findViewById(R.id.text_phone);
        EditText mTextPwd1=(EditText)findViewById(R.id.text_pwd);

        Drawable phone=ContextCompat.getDrawable(this,R.drawable.icon_telphone_32);//调整 我的电话 图片大小
        phone.setBounds(0,0,180,180);
        mTextPhone1.setCompoundDrawables(phone,null,null,null);

        Drawable password=ContextCompat.getDrawable(this,R.drawable.icon_lock);//调整 我的电话 图片大小
        password.setBounds(0,0,180,180);
        mTextPwd1.setCompoundDrawables(password,null,null,null);


        ViewUtils.inject(this);
        initToolBar();



    }

    private void initToolBar(){
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LoginActivity.this.finish();

            }
        });
    }

    @Override
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

}

package com.example.myworkone4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.myworkone4.bean.User;
import com.example.myworkone4.fragment.MineFragment;
import com.example.myworkone4.http.DBOpenHelper;
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

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class RegActivity extends AppCompatActivity {

    private DBOpenHelper mDBOpenHelper;

    @ViewInject(R.id.toolbar)
    private CnToolbar mToolBar;

    @ViewInject(R.id.edittext_phone)
    private ClearEditText mTextPhone;

    @ViewInject(R.id.edittext_pwd)
    private ClearEditText mTextPwd;

   // private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ViewUtils.inject(this);
       // initToolBar();
        mDBOpenHelper = new DBOpenHelper(getApplicationContext());

    }

   /* private void initToolBar(){
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               RegActivity.this.finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @OnClick(R.id.btn_reg)
    private void doReg(View view){

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

        okHttpHelper.post(Contants.API.REG, params, new SpotsCallBack<LoginRespMsg<User>>(this) {

            @Override
            public void onSuccess(okhttp3.Response response, LoginRespMsg<User> userLoginRespMsg) {//登录成功后把user信息和token验证码保存到本地
                CniaoApplication application =  CniaoApplication.getInstance();
                application.putUser(userLoginRespMsg.getData(), userLoginRespMsg.getToken());
                if(application.getIntent() == null){
                    setResult(RESULT_OK);//返回码
                    Toast.makeText(RegActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    application.jumpToTargetActivity(RegActivity.this);
                    finish();
                }
            }

            @Override
            public void onError(okhttp3.Response response, int code, Exception e) {
            }
        });
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.btn_reg)
    private void doReg(View view){
        String phone = mTextPhone.getText().toString().trim();
        String password = mTextPwd.getText().toString().trim();
        //Toast.makeText(this,phone, Toast.LENGTH_SHORT).show();

        //注册验证
       if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)  ) {

            //将用户名和密码加入到数据库中
            mDBOpenHelper.add(phone,password);
            Intent intent2 = new Intent(this, MineFragment.class);
           setResult(RESULT_OK);//返回码
            finish();
            Toast.makeText(this,  "验证通过，注册成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
        }
    }
}

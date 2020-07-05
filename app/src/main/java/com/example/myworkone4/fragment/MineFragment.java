package com.example.myworkone4.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;

import com.example.myworkone4.AddressListActivity;
import com.example.myworkone4.CniaoApplication;
import com.example.myworkone4.Contants;
import com.example.myworkone4.LoginActivity;
import com.example.myworkone4.R;
import com.example.myworkone4.bean.User;
import com.example.myworkone4.weiget.ClearEditText;
import com.lidroid.xutils.view.annotation.PreferenceInject;
import com.lidroid.xutils.view.annotation.ResInject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.EventBase;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class MineFragment extends BaseFragment{
    @ViewInject(R.id.img_head)
    private CircleImageView mImageHead;

    @ViewInject(R.id.txt_username)
    private TextView mTxtUserName;

    @ViewInject(R.id.btn_logout)
    private Button mbtnLogout;

    @ViewInject(R.id.txt_my_orders)//我的订单
            TextView myorder;

    @ViewInject(R.id.txt_my_favorite)//我的收藏
            TextView myfavorite;

    @ViewInject(R.id.txt_my_location)//我的收货地址
            TextView mylocation;

    @ViewInject(R.id.txt_my_message)//我的消息
            TextView mymessage;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.fragment_mine,container,false);
    }

    @Override
    public void init() {

        Drawable order=ContextCompat.getDrawable(getActivity(),R.drawable.icon_list_o);//调整 我的订单 图片大小
        order.setBounds(0,0,180,180);
        myorder.setCompoundDrawables(order,null,null,null);

        Drawable favorite=ContextCompat.getDrawable(getActivity(),R.drawable.icon_favorite);//调整 我的收藏 图片大小
        favorite.setBounds(0,0,180,180);
        myfavorite.setCompoundDrawables(favorite,null,null,null);

        Drawable location=ContextCompat.getDrawable(getActivity(),R.drawable.icon_location);//调整 我的收货地址 图片大小
        location.setBounds(0,0,180,180);
        mylocation.setCompoundDrawables(location,null,null,null);

        Drawable message=ContextCompat.getDrawable(getActivity(),R.drawable.icon_msg);//调整 我的消息 图片大小
        message.setBounds(0,0,180,180);
        mymessage.setCompoundDrawables(message,null,null,null);

        mTxtUserName.setText(R.string.to_login);
        mbtnLogout.setVisibility(View.GONE);

    }

    @OnClick(value = {R.id.img_head,R.id.txt_username})//点击，登录
    public void toLogin(View view){

        if(CniaoApplication.getInstance().getUser() == null) {
            Intent intent = new Intent(getContext(), LoginActivity.class);//跳转到登录界面进行登录
            startActivityForResult(intent, Contants.REQUEST_CODE);//请求码，登录成功后可以跳转回来

        }

    }

    @OnClick(R.id.btn_logout)//点击退出登录
    public void logout(View view){

        CniaoApplication.getInstance().clearUser();
        mTxtUserName.setText(R.string.to_login);
        mbtnLogout.setVisibility(View.GONE);
        Picasso.with(getActivity()).load(R.drawable.default_head).into(mImageHead);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        User user =  CniaoApplication.getInstance().getUser();
        showUser(user);//登录后要显示头像和用户名
    }

    private void showUser(User user){
        /*if(user!=null){
            if(!TextUtils.isEmpty(user.getLogo_url()))
                showHeadImage(user.getLogo_url());

            mTxtUserName.setText(user.getUsername());

            mbtnLogout.setVisibility(View.VISIBLE);
        }
        else {
            mTxtUserName.setText(R.string.login);
            mbtnLogout.setVisibility(View.VISIBLE);
        }*/
        mTxtUserName.setText(R.string.login1);
        mbtnLogout.setVisibility(View.VISIBLE);
        Picasso.with(getActivity()).load(R.drawable.img_head1).into(mImageHead);
    }

    @OnClick(R.id.txt_my_location)//点击收货地址
    public void setLocation(View view){

        Intent intent = new Intent(getContext(), AddressListActivity.class);//跳转到收货地址页面
        startActivity(intent);
    }


}

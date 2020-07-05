package com.example.myworkone4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myworkone4.adapter.AddressAdapter;
import com.example.myworkone4.adapter.CategoryAdapter;
import com.example.myworkone4.adapter.WaresAdapter;
import com.example.myworkone4.adapter.decoration.DividerGridItemDecoration;
import com.example.myworkone4.adapter.decoration.DividerItemDecoration;
import com.example.myworkone4.bean.Address;
import com.example.myworkone4.http.OkHttpHelper;

import com.example.myworkone4.http.SpotsCallBack;
import com.example.myworkone4.msg.BaseRespMsg;
import com.example.myworkone4.weiget.CnToolbar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import okhttp3.Response;

import static java.security.AccessController.getContext;


public class AddressListActivity extends AppCompatActivity {

    @ViewInject(R.id.toolbar)
    private CnToolbar mToolBar;

    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerview;

    @ViewInject(R.id.txt_name)
    private TextView mName;

    @ViewInject(R.id.txt_phone)
    private TextView mPhone;

    @ViewInject(R.id.txt_address)
    private TextView mAddress;







private List<Address> address=new ArrayList<>();


    private AddressAdapter mAdapter;

    private OkHttpHelper mHttpHelper=OkHttpHelper.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

        ViewUtils.inject(this);



        SharedPreferences settings=getPreferences(Activity.MODE_PRIVATE);//装载数据
        String name=settings.getString("name","");//取得值
        //String phone=settings.getString("phone","");
       // String address=settings.getString("address","");
        mName.setText(name);//赋予值给指定控件
        //mName.setText(phone);
        //mName.setText(address);

        initToolbar();
    }



    private void initToolbar() {//返回或者添加地址
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences uiState=getPreferences(0);//取得活动的Preferences对象
                SharedPreferences.Editor editor=uiState.edit();//取得编辑对象
                editor.putString("name",mName.getText().toString()+"   "+mPhone.getText().toString()+"\n"+mAddress.getText().toString());//添加值
                //editor.putString("phone",mPhone.getText().toString());
                //editor.putString("address",mAddress.getText().toString());
                editor.commit();//保存提交
                finish();
            }
        });
        mToolBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toAddActivity();
            }
        });

   }

    private void toAddActivity() {

        Intent intent = new Intent(this,AddressAddActivity.class);
        startActivityForResult(intent,1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {





        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&resultCode==2){


            String acquiredData1=data.getStringExtra("consignee");//获取回传数据
            //Address address1=new Address(acquiredData1,0);

            String acquiredData2=data.getStringExtra("phone");//获取回传数据
            //Address address2=new Address(acquiredData2,0);

            String acquiredData3=data.getStringExtra("addr");//获取回传数据
            //Address address3=new Address(acquiredData3,0);

            //address.add(address1);
            //address.add(address2);
            //address.add(address3);

           // LinearLayoutManager layoutManager=new LinearLayoutManager(this);
           // mRecyclerview.setLayoutManager(layoutManager);
           // mAdapter = new AddressAdapter(this,address);
           // mRecyclerview.setAdapter(mAdapter);




            mName.setText("收件人姓名："+acquiredData1);
           mPhone.setText("联系电话："+acquiredData2);
            mAddress.setText("收货地址："+acquiredData3);
            //Toast.makeText(AddressListActivity.this,acquiredData1,Toast.LENGTH_SHORT).show();

        }
    }

}

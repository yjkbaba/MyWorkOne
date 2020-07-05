package com.example.myworkone4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.myworkone4.city.XmlParserHandler;
import com.example.myworkone4.city.model.CityModel;
import com.example.myworkone4.city.model.DistrictModel;
import com.example.myworkone4.city.model.ProvinceModel;
import com.example.myworkone4.http.OkHttpHelper;
import com.example.myworkone4.http.SpotsCallBack;
import com.example.myworkone4.msg.BaseRespMsg;
import com.example.myworkone4.weiget.ClearEditText;
import com.example.myworkone4.weiget.CnToolbar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Response;

public class AddressAddActivity extends AppCompatActivity {

    private OptionsPickerView mCityPikerView; //https://github.com/saiwu-bigkoo/Android-PickerView


    @ViewInject(R.id.txt_address)
    private TextView mTxtAddress;

    @ViewInject(R.id.edittxt_consignee)
    private ClearEditText mEditConsignee;

    @ViewInject(R.id.edittxt_phone)
    private ClearEditText mEditPhone;

    @ViewInject(R.id.edittxt_add)
    private ClearEditText mEditAddr;

    @ViewInject(R.id.toolbar)
    private CnToolbar mToolBar;




    private List<ProvinceModel> mProvinces;
    private ArrayList<ArrayList<String>> mCities = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> mDistricts = new ArrayList<ArrayList<ArrayList<String>>>();


    private OkHttpHelper mHttpHelper=OkHttpHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_add);

        ViewUtils.inject(this);


        initToolbar();
        init();
    }

    private void initToolbar(){

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mToolBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAddress();
            }
        });

    }

    private void init() {

        initProvinceDatas();

        mCityPikerView = new OptionsPickerView(this);

        mCityPikerView.setPicker((ArrayList) mProvinces,mCities,mDistricts,true);//选择省、市、地区，以及是否级联
        mCityPikerView.setTitle("选择城市");
        mCityPikerView.setCyclic(false,false,false);//设置不能循环滚动
        mCityPikerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {

                String addresss = mProvinces.get(options1).getName() +"  "
                        + mCities.get(options1).get(option2)+"  "
                        + mDistricts.get(options1).get(option2).get(options3);
                        mTxtAddress.setText(addresss);

            }
        });

    }

    protected void initProvinceDatas() {

        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            mProvinces = handler.getDataList();

        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }

        if(mProvinces !=null){

            for (ProvinceModel p :mProvinces){

                List<CityModel> cities =p.getCityList();
                ArrayList<String> cityStrs = new ArrayList<>(cities.size()); //城市List


                for (CityModel c :cities){

                    cityStrs.add(c.getName()); // 把城市名称放入 cityStrs


                    ArrayList<ArrayList<String>> dts = new ArrayList<>(); // 地区 List

                    List<DistrictModel> districts =c.getDistrictList();
                    ArrayList<String> districtStrs = new ArrayList<>(districts.size());

                    for (DistrictModel d : districts){
                        districtStrs.add(d.getName()); // 把地区名称放入 districtStrs
                    }
                    dts.add(districtStrs);


                    mDistricts.add(dts);
                }

                mCities.add(cityStrs); // 组装城市数据

            }
        }
    }

    @OnClick(R.id.ll_city_picker)
    public void showCityPickerView(View view){
        mCityPikerView.show();
    }


    public void createAddress(){


        String consignee = mEditConsignee.getText().toString();
        String phone = mEditPhone.getText().toString();
        String address = mTxtAddress.getText().toString() + mEditAddr.getText().toString();

        Intent intent =new Intent();
        intent.putExtra("consignee",consignee);
        intent.putExtra("phone",phone);
        intent.putExtra("addr",address);

        setResult(2,intent);
        finish();


    }
}

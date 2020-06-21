package com.example.myworkone4.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.myworkone4.R;
import com.example.myworkone4.adapter.DividerItemDecortion;
import com.example.myworkone4.bean.Banner;
import com.example.myworkone4.bean.HomeCategory;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.example.myworkone4.adapter.HomeCatgoryAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;


/**
 * Created by Ivan on 15/9/25.
 */
public class HomeFragment extends Fragment {

    private SliderLayout mSliderLayout;
    private PagerIndicator indicator;
    private RecyclerView mRecyclerView;
    private HomeCatgoryAdapter mAdatper;
    private static  final  String TAG="HomeFragment";
    private Gson mGson = new Gson();
    private List<Banner> mBanner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home,container,false);
        mSliderLayout= view.findViewById(R.id.slider);
        indicator=view.findViewById(R.id.custom_indicator);
        requestImages();


        initRecyclerView(view);

        return view;
    }

    private void requestImages(){
        String url ="http://112.124.22.238:8081/course_api/banner/query";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("type","1")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String json;
                        json = response.body().string();
                        Type type = new TypeToken<List<Banner>>(){}.getType();
                        mBanner = mGson.fromJson(json,type);
                        initSlider();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        List<HomeCategory> datas = new ArrayList<>(15);

        HomeCategory category = new HomeCategory("热门活动",R.drawable.img_big_1,R.drawable.img_1_small1,R.drawable.img_1_small2);
        datas.add(category);

        category = new HomeCategory("有利可图",R.drawable.img_big_4,R.drawable.img_4_small1,R.drawable.img_4_small2);
        datas.add(category);
        category = new HomeCategory("品牌街",R.drawable.img_big_2,R.drawable.img_2_small1,R.drawable.img_2_small2);
        datas.add(category);

        category = new HomeCategory("金融街 包赚翻",R.drawable.img_big_1,R.drawable.img_3_small1,R.drawable.imag_3_small2);
        datas.add(category);

        category = new HomeCategory("超值购",R.drawable.img_big_0,R.drawable.img_0_small1,R.drawable.img_0_small2);
        datas.add(category);

        mAdatper = new HomeCatgoryAdapter(datas);

        mRecyclerView.setAdapter(mAdatper);
        mRecyclerView.addItemDecoration(new DividerItemDecortion());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

    }

    private void initSlider(){

        if(mBanner !=null){

            for (Banner banner : mBanner){
                TextSliderView textSliderView = new TextSliderView(this.getActivity());
                textSliderView.image(banner.getImgUrl());
                textSliderView.description(banner.getName());
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(textSliderView);

            }
        }








        //mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//底部中间位置
        mSliderLayout.setCustomIndicator(indicator);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Stack);//设置轮播样式
        mSliderLayout.setDuration(3000);//设置时间





        mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG,"onPageScrolled");
            }
            @Override
            public void onPageSelected(int position) {
                Log.d(TAG,"onPageSelected");
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG,"onPageScrollStateChanged");
            }
        });





    }




}

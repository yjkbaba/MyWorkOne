package com.example.myworkone4.fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.example.myworkone4.bean.HomeCategory;

import java.util.ArrayList;
import java.util.List;
import com.example.myworkone4.adapter.HomeCatgoryAdapter;
import static android.content.ContentValues.TAG;


/**
 * Created by Ivan on 15/9/25.
 */
public class HomeFragment extends Fragment {

    private SliderLayout mSliderLayout;
    private PagerIndicator indicator;
    private RecyclerView mRecyclerView;
    private HomeCatgoryAdapter mAdatper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home,container,false);
        mSliderLayout= view.findViewById(R.id.slider);
        indicator=view.findViewById(R.id.custom_indicator);

        initSlider();

        initRecyclerView(view);

        return view;
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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

    }

    private void initSlider(){
        TextSliderView textSliderView=new TextSliderView(this.getActivity());
        textSliderView.image("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=43784219,2537123848&fm=26&gp=0.jpg");
        textSliderView.description("定向运动");
        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {//监听
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(HomeFragment.this.getActivity(),"定向运动",Toast.LENGTH_SHORT).show();
            }
        });

        TextSliderView textSliderView2=new TextSliderView(this.getActivity());
        textSliderView2.image("http://img5.imgtn.bdimg.com/it/u=4206316809,2746013448&fm=26&gp=0.jpg");
        textSliderView2.description("场地");
        textSliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {//监听
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(HomeFragment.this.getActivity(),"场地",Toast.LENGTH_SHORT).show();
            }
        });

        TextSliderView textSliderView3=new TextSliderView(this.getActivity());
        textSliderView3.image("http://img3.imgtn.bdimg.com/it/u=1761171978,2076887877&fm=26&gp=0.jpg");
        textSliderView3.description("指北针");
        textSliderView3.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {//监听
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(HomeFragment.this.getActivity(),"指北针",Toast.LENGTH_SHORT).show();
            }
        });



        mSliderLayout.addSlider(textSliderView);
        mSliderLayout.addSlider(textSliderView2);
        mSliderLayout.addSlider(textSliderView3);

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

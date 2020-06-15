package com.example.myworkone4.fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.myworkone4.R;

import static android.content.ContentValues.TAG;


/**
 * Created by Ivan on 15/9/25.
 */
public class HomeFragment extends Fragment {

    private SliderLayout mSliderLayout;
    private PagerIndicator indicator;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home,container,false);
        mSliderLayout= view.findViewById(R.id.slider);
        indicator=view.findViewById(R.id.custom_indicator);

        initSlider();
        return view;
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

package com.example.myworkone4.fragment;
import android.content.Entity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.myworkone4.Contants;
import com.example.myworkone4.R;
import com.example.myworkone4.adapter.DividerItemDecortion;
import com.example.myworkone4.bean.Banner;
import com.example.myworkone4.bean.Campaign;
import com.example.myworkone4.bean.HomeCampaign;

import java.util.ArrayList;
import java.util.List;
import com.example.myworkone4.adapter.HomeCatgoryAdapter;

import com.example.myworkone4.http.BaseCallback;
import com.example.myworkone4.http.OkHttpHelper;
import com.example.myworkone4.http.SpotsCallBack;
import com.google.gson.Gson;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Ivan on 15/9/25.
 */
public class HomeFragment extends Fragment {


    private SliderLayout mSliderLayout;
    private RecyclerView mRecyclerView;
    private HomeCatgoryAdapter mAdatper;
    private static  final  String TAG="HomeFragment";
    private Gson mGson = new Gson();
    private List<Banner> mBanner;
    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        mSliderLayout= view.findViewById(R.id.slider);
        requestImages();
        initRecyclerView(view);
        return view;
    }
    private void requestImages(){//轮播广告
        String url ="http://112.124.22.238:8081/course_api/banner/query?type=1";
        httpHelper.get(url, new SpotsCallBack<List<Banner>>(getContext()){
            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mBanner = banners;
                initSlider();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void initRecyclerView(View view) {//首页商品

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        httpHelper.get(Contants.API.CAMPAIGN_HOME, new BaseCallback<List<HomeCampaign>>() {
            @Override
            public void onBeforeRequest(Request request) {
            }
            @Override
            public void onFailure(Request request, Exception e) {
            }
            @Override
            public void onResponse(Response response) {
            }
            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {

                initData(homeCampaigns);
            }
            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private  void initData(List<HomeCampaign> homeCampaigns){

        mAdatper = new HomeCatgoryAdapter(homeCampaigns,getActivity());
        mAdatper.SetOnCampaignClickListener(new HomeCatgoryAdapter.OnCampaignClickListener(){
            public void onClick(View view, Campaign campaign){
                Toast.makeText(getContext(),"title="+campaign.getTitle(),Toast.LENGTH_SHORT).show();
                //应该在这里实现页面跳转！
            }
        });

        mRecyclerView.addItemDecoration(new DividerItemDecortion());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));//布局管理器
        mRecyclerView.setAdapter(mAdatper);//适配器


    }

    private void initSlider(){//轮播广告
        if(mBanner !=null){
            for (Banner banner : mBanner){
                TextSliderView textSliderView = new TextSliderView(this.getActivity());
                textSliderView.image(banner.getImgUrl());//获取图片
                textSliderView.description(banner.getName());//获取名字
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(textSliderView);

            }
        }
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//底部中间位置
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);//设置轮播样式
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
    @Override
    public void onDestroy() {
        super.onDestroy();

        mSliderLayout.stopAutoCycle();
    }



}

package com.example.myworkone4.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.myworkone4.Contants;
import com.example.myworkone4.R;


import com.example.myworkone4.adapter.CategoryAdapter;
import com.example.myworkone4.bean.Banner;
import com.example.myworkone4.bean.Category;
import com.example.myworkone4.bean.HomeCampaign;
import com.example.myworkone4.http.BaseCallback;
import com.example.myworkone4.http.OkHttpHelper;
import com.example.myworkone4.http.SpotsCallBack;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


import java.util.List;

import kotlin.random.Random;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.myworkone4.http.OkHttpHelper.TAG;


/**
 * Created by Ivan on 15/9/22.
 */
public class CategoryFragment extends Fragment {
    @ViewInject(R.id.recyclerview_category)
    private RecyclerView mRecyclerView;
    private CategoryAdapter mCategoryAdapter;//定义适配器

    @ViewInject(R.id.slider)
    private SliderLayout mSliderLayout;
    private OkHttpHelper mHttpHelper=OkHttpHelper.getInstance();//从服务器取得数据

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category,container,false);
        ViewUtils.inject(this,view);

        requestCategoryData();//调用一级菜单的方法
        requestBannerData();//调用轮播广告的方法

        return  view;
    }

    private  void requestCategoryData() {//请求一级菜单数据
        mHttpHelper.get(Contants.API.CATEGORY_LIST, new SpotsCallBack<List<Category>>(getContext()) {
            @Override
            public void onSuccess(Response response, List<Category> categories) {
                showCategoryData(categories);
            }
            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private  void showCategoryData(List<Category> categories){//显示一级菜单数据
             mCategoryAdapter = new CategoryAdapter(getContext(),categories);
             mRecyclerView.setItemAnimator(new DefaultItemAnimator());
             mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));//分隔线
             mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//布局管理器
             mRecyclerView.setAdapter(mCategoryAdapter);//适配器
    }

    private void requestBannerData(){//获取轮播广告数据
        String url = Contants.API.BANNER+"?type=1";
        mHttpHelper.get(url, new SpotsCallBack<List<Banner>>(getContext()){
            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                showSliderViews(banners);
            }
            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private void showSliderViews(List<Banner>banners){//显示轮播广告
        if(banners !=null){
            for (Banner banner : banners){
                DefaultSliderView sliderView = new DefaultSliderView(this.getActivity());
                sliderView.image(banner.getImgUrl());//获取图片
                sliderView.description(banner.getName());//DefaultSliderView没有获取名字
                sliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(sliderView);
            }
        }
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//底部中间位置
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Stack);//设置轮播样式
        mSliderLayout.setDuration(3000);//设置时间
    }

}

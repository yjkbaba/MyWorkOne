package com.example.myworkone4.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import com.example.myworkone4.Contants;
import com.example.myworkone4.R;

import com.example.myworkone4.adapter.BaseAdapter;
import com.example.myworkone4.adapter.CategoryAdapter;
import com.example.myworkone4.adapter.WaresAdapter;
import com.example.myworkone4.adapter.decoration.DividerGridItemDecoration;
import com.example.myworkone4.bean.Banner;
import com.example.myworkone4.bean.Category;

import com.example.myworkone4.bean.Page;
import com.example.myworkone4.bean.Wares;
import com.example.myworkone4.http.BaseCallback;
import com.example.myworkone4.http.OkHttpHelper;
import com.example.myworkone4.http.SpotsCallBack;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class CategoryFragment extends Fragment {
    @ViewInject(R.id.recyclerview_category)
    private RecyclerView mRecyclerView;
    private CategoryAdapter mCategoryAdapter;//定义一级菜单的适配器

    @ViewInject(R.id.recyclerview_wares)
    private RecyclerView mRecyclerviewWares;
    private WaresAdapter mWaresAdatper;//定义二级菜单的适配器

    @ViewInject(R.id.slider)
    private SliderLayout mSliderLayout;//下拉刷新

    @ViewInject(R.id.refresh_layout)
    private MaterialRefreshLayout mRefreshLaout;//下拉刷新

    private OkHttpHelper mHttpHelper=OkHttpHelper.getInstance();//从服务器取得数据

    private int currPage=1;
    private int totalPage=1;
    private int pageSize=10;
    private long category_id=0;

    private  static final int STATE_NORMAL=0;
    private  static final int STATE_REFREH=1;
    private  static final int STATE_MORE=2;

    private int state=STATE_NORMAL;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category,container,false);
        ViewUtils.inject(this,view);

        requestCategoryData();//调用一级菜单的方法
        requestBannerData();//调用轮播广告的方法
        initRefreshLayout();//调用下拉刷新，上拉加载方法

        return  view;
    }

    private  void initRefreshLayout(){
        mRefreshLaout.setLoadMore(true);
        mRefreshLaout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();//下拉刷新
                Toast.makeText(getContext(),"已刷新",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {

                if(currPage <=totalPage){
                    loadMoreData();//上拉加载
                    Toast.makeText(getContext(),"已刷新",Toast.LENGTH_SHORT).show();
                   }

                else{
                    mRefreshLaout.finishRefreshLoadMore();
                    Toast.makeText(getContext(),"没有数据啦",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private  void refreshData(){
        currPage =1;
        state=STATE_REFREH;
        requestWares(category_id);
    }

    private void loadMoreData(){
        currPage = ++currPage;
        state = STATE_MORE;
        requestWares(category_id);
    }

    private  void requestCategoryData() {//请求一级菜单数据
        mHttpHelper.get(Contants.API.CATEGORY_LIST, new SpotsCallBack<List<Category>>(getContext()) {
            @Override
            public void onSuccess(Response response, List<Category> categories) {
                showCategoryData(categories);
                if(categories !=null && categories.size()>0)
                    category_id = categories.get(0).getId();
                    requestWares(category_id);//调用获取二级菜单方法，默认显示第一个二级菜单
            }
            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });
    }

    private  void showCategoryData(List<Category> categories){//显示一级菜单数据
        mCategoryAdapter = new CategoryAdapter(getContext(),categories);
        mCategoryAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {//设置点击事件
            @Override
            public void onItemClick(View view, int position) {
                Category category=mCategoryAdapter.getItem(position);
                category_id=category.getId();
                currPage=1;
                state=STATE_NORMAL;
                requestWares(category_id);//调用获取二级菜单商品
            }
        });
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

    private void requestWares(long categoryId){//获取二级菜单商品

        String url = Contants.API.WARES_LIST+"?categoryId="+categoryId+"&curPage="+currPage+"&pageSize="+pageSize;//二级菜单地址

        mHttpHelper.get(url, new BaseCallback<Page<Wares>>() {
            @Override
            public void onBeforeRequest(Request request) { }

            @Override
            public void onFailure(Request request, Exception e) { }

            @Override
            public void onResponse(Response response) { }

            @Override
            public void onSuccess(Response response, Page<Wares> waresPage) {
                currPage = waresPage.getCurrentPage();
                totalPage =waresPage.getTotalCount();//这里可能是getTotalPage???
                showWaresData(waresPage.getList());//调用显示二级菜单商品
            }

            @Override
            public void onError(Response response, int code, Exception e) { }
        });
    }

    private  void showWaresData(List<Wares> wares){//显示二级菜单商品
        switch (state){

            case  STATE_NORMAL:
                if(mWaresAdatper ==null) {
                    mWaresAdatper = new WaresAdapter(getContext(),wares);
                    mRecyclerviewWares.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerviewWares.addItemDecoration(new DividerGridItemDecoration(getContext()));//加网格的分隔线
                    mRecyclerviewWares.setLayoutManager(new GridLayoutManager(getContext(),2));//网格布局，每行两个
                    mRecyclerviewWares.setAdapter(mWaresAdatper);

                }
                else{
                    mWaresAdatper.clear();
                    mWaresAdatper.addData(wares);
                }

                break;

            case STATE_REFREH:
                mWaresAdatper.clear();
                mWaresAdatper.addData(wares);

                mRecyclerviewWares.scrollToPosition(0);
                mRefreshLaout.finishRefresh();
                break;

            case STATE_MORE:
                mWaresAdatper.addData(mWaresAdatper.getDatas().size(),wares);
                mRecyclerviewWares.scrollToPosition(mWaresAdatper.getDatas().size());
                mRefreshLaout.finishRefreshLoadMore();
                break;

        }



    }


}

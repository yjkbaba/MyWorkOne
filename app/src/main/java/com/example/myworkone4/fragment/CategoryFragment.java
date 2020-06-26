package com.example.myworkone4.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.example.myworkone4.Contants;
import com.example.myworkone4.R;


import com.example.myworkone4.adapter.CategoryAdapter;
import com.example.myworkone4.bean.Category;
import com.example.myworkone4.http.OkHttpHelper;
import com.example.myworkone4.http.SpotsCallBack;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


import java.util.List;

import okhttp3.Response;




/**
 * Created by Ivan on 15/9/22.
 */
public class CategoryFragment extends Fragment {

    @ViewInject(R.id.recyclerview_category)
    private RecyclerView mRecyclerView;
    private CategoryAdapter mCategoryAdapter;//定义适配器

    private OkHttpHelper mHttpHelper=OkHttpHelper.getInstance();//从服务器取得数据

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category,container,false);
        ViewUtils.inject(this,view);

        requestCategoryData();

        return  view;
    }

    private  void requestCategoryData() {//请求数据
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

    private  void showCategoryData(List<Category> categories){  //显示数据


        mCategoryAdapter = new CategoryAdapter(getContext(),categories);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));//分隔线
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//布局管理器

        mRecyclerView.setAdapter(mCategoryAdapter);//适配器
    }


}




package com.example.myworkone4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.myworkone4.bean.Tab;
import com.example.myworkone4.fragment.CartFragment;
import com.example.myworkone4.fragment.CategoryFragment;
import com.example.myworkone4.fragment.HomeFragment;
import com.example.myworkone4.fragment.MineFragment;
import com.example.myworkone4.weiget.CnToolbar;
import com.example.myworkone4.weiget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private LayoutInflater mInflater;
    private FragmentTabHost mTabHost;
    private CnToolbar mToolbar;
    private CartFragment cartFragment;
    private List<Tab> mTabs = new ArrayList<>(4);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        initTab();
    }
    private void initToolBar() {
        mToolbar = (CnToolbar) findViewById(R.id.toolbar);
    }

    private void initTab() {
        Tab tab_home = new Tab(HomeFragment.class,R.string.home,R.drawable.selector_icon_home);
        Tab tab_category = new Tab(CategoryFragment.class,R.string.catagory,R.drawable.selector_icon_category);
        Tab tab_cart = new Tab(CartFragment.class,R.string.cart,R.drawable.selector_icon_cart);
        Tab tab_mine = new Tab(MineFragment.class,R.string.mine,R.drawable.selector_icon_mine);
        mTabs.add(tab_home);
        mTabs.add(tab_category);
        mTabs.add(tab_cart);
        mTabs.add(tab_mine);
        mInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
        for(Tab tab : mTabs){
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabHost.addTab(tabSpec,tab.getFragment(),null);
        }
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId==getString(R.string.cart)){
                    refData();
                }
                else{

                    mToolbar.showSearchView();
                    mToolbar.hideTitleView();
                    mToolbar.getRightButton().setVisibility(View.GONE);

                }
            }
        });

        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTab(0);
    }
    private void refData(){

        if(cartFragment ==null) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(getString(R.string.cart));
            if (fragment != null) {
                cartFragment = (CartFragment) fragment;
                cartFragment.refData();
                cartFragment.changeToolbar();
            }
        }
        else
        {
            cartFragment.refData();
            cartFragment.changeToolbar();
        }
    }

    private View buildIndicator(Tab tab){
        View view = mInflater.inflate(R.layout.tab_indicator,null);
        ImageView img = view.findViewById(R.id.icon_tab);
        TextView text = view.findViewById(R.id.txt_indicator);
        img.setImageResource(tab.getIcon());
        text.setText(tab.getTitle());
        return view;
    }
}
package com.view.jameson.androidrecyclerviewcard;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.view.jameson.library.BannerRecyclerView;
import com.view.jameson.library.BannerScaleHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * changed by 二精-霁雪清虹 on 2017/11/19
 */
public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    protected SwipeRefreshLayout mSwipeLayout;
    private BannerRecyclerView mRecyclerView;
    private List<Integer> mList = new ArrayList<>();
    private BannerScaleHelper mBannerScaleHelper = null;
    private CardAdapter mAdapter;
    public static String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        super.setContentView(R.layout.activity_main);
        initView();
        init();
    }

    private void init() {
        for (int i = 0; i < 3; i++) {
            mList.add(R.drawable.pic4);
            mList.add(R.drawable.pic5);
            mList.add(R.drawable.pic6);
        }

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CardAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        // mRecyclerView绑定scale效果
        mBannerScaleHelper = new BannerScaleHelper();
        mBannerScaleHelper.setFirstItemPos(1000);
        mBannerScaleHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Log.e("TAG", "postDelayed scrollToPosition" );
                mBannerScaleHelper.scrollToPosition(4);
            }
        }, 2000);

    }

    private void initView() {
        mRecyclerView = (BannerRecyclerView) findViewById(R.id.recyclerView);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);

        //为SwipeRefreshLayout设置监听事件
        mSwipeLayout.setOnRefreshListener(this);
        //为SwipeRefreshLayout设置刷新时的颜色变化，最多可以设置4种
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onRefresh() {
        //刷新
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //结束后停止刷新
                mSwipeLayout.setRefreshing(false);
                mList.clear();
                for (int i = 0; i < 2; i++) {
                    mList.add(R.drawable.pic6);
                    mList.add(R.drawable.pic4);
                }

                //如果需要带缩放的scale需要调用一下，否则缩放效果会出现缩放误差
                //mRecyclerView.smoothScrollToPosition(mBannerScaleHelper.getCurrentItem());
                mBannerScaleHelper.setCurrentItem(mBannerScaleHelper.getCurrentItem(),true);

                mAdapter.setList(mList);
                mAdapter.notifyDataSetChanged();
            }
        }, 1500);
    }
}

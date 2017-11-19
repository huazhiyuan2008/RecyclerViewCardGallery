package com.view.jameson.androidrecyclerviewcard;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.view.jameson.library.BannerRecyclerView;
import com.view.jameson.library.BannerScaleHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * changed by 二精-霁雪清虹 on 2017/11/19
 */
public class MainActivity extends Activity {

    private BannerRecyclerView mRecyclerView;
    private List<Integer> mList = new ArrayList<>();
    private BannerScaleHelper mBannerScaleHelper = null;

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
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        for (int i = 0; i < 3; i++) {
            mList.add(R.drawable.pic4);
            mList.add(R.drawable.pic5);
            mList.add(R.drawable.pic6);
        }

        mRecyclerView = (BannerRecyclerView) findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new CardAdapter(mList));
        // mRecyclerView绑定scale效果
        mBannerScaleHelper = new BannerScaleHelper();
        mBannerScaleHelper.setFirstItemPos(1000);
        mBannerScaleHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Log.e("TAG", "postDelayed scrollToPosition" );
                mBannerScaleHelper.scrollToPosition(2000);
            }
        }, 2000);
    }
}

package com.view.jameson.androidrecyclerviewcard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import jameson.io.library.util.ScreenUtil;
import jameson.io.library.util.ToastUtils;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        new LinearSnapHelper().attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(new CardAdapter());
    }

    private class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
        private List<Integer> mList = new ArrayList<>();

        public CardAdapter() {
            for (int i = 0; i < 20; i++) {
                mList.add(R.drawable.pic4);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mImageView.setImageResource(mList.get(position));
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.show(holder.mImageView.getContext(), "" + position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final ImageView mImageView;

            public ViewHolder(final View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView.findViewById(R.id.imageView);

                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                lp.width = ScreenUtil.getScreenWidth(itemView.getContext()) - ScreenUtil.dip2px(itemView.getContext(), 60f);
                itemView.setLayoutParams(lp);
            }

        }

    }
}

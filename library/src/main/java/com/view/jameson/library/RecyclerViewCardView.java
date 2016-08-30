package com.view.jameson.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by jameson on 8/30/16.
 */
public class RecyclerViewCardView extends LinearLayout {

     private RecyclerView mRecyclerView = null;

    public RecyclerViewCardView(Context context) {
        super(context);
    }

    public RecyclerViewCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }
}

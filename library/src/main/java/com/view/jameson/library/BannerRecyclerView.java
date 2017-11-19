package com.view.jameson.library;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制fling速度的RecyclerView
 * <p>
 * Created by jameson on 9/1/16.
 * changed by 二精-霁雪清虹 on 2017/11/19
 */
public class BannerRecyclerView extends RecyclerView {

    private static final float FLING_SCALE_DOWN_FACTOR = 0.5f; // 减速因子
    private static final int FLING_MAX_VELOCITY = 8000; // 最大顺时滑动速度
    private static boolean mEnableLimitVelocity = true; // 最大顺时滑动速度
    private List<OnPageChangeListener> mOnPageChangeListeners;
    private OnPageChangeListener mOnPageChangeListener;

    public BannerRecyclerView(Context context) {
        super(context);
    }

    public BannerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static boolean ismEnableLimitVelocity() {
        return mEnableLimitVelocity;
    }

    public static void setmEnableLimitVelocity(boolean mEnableLimitVelocity) {
        BannerRecyclerView.mEnableLimitVelocity = mEnableLimitVelocity;
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        if (mEnableLimitVelocity) {
            velocityX = solveVelocity(velocityX);
            velocityY = solveVelocity(velocityY);
        }
        return super.fling(velocityX, velocityY);
    }

    private int solveVelocity(int velocity) {
        if (velocity > 0) {
            return Math.min(velocity, FLING_MAX_VELOCITY);
        } else {
            return Math.max(velocity, -FLING_MAX_VELOCITY);
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

    public void addOnPageChangeListener(OnPageChangeListener listener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = new ArrayList<>();
        }
        mOnPageChangeListeners.add(listener);
    }

    public void removeOnPageChangeListener(OnPageChangeListener listener) {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.remove(listener);
        }
    }

    public void clearOnPageChangeListeners() {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.clear();
        }
    }


    public interface OnPageChangeListener {
        void onPageSelected(int position);
    }

    public void dispatchOnPageSelected(int position) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        if (mOnPageChangeListeners != null) {
            for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                if (listener != null) {
                    listener.onPageSelected(position);
                }
            }
        }
    }

}

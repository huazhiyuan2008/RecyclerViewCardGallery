package com.view.jameson.library;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import jameson.io.library.util.ScreenUtil;

/**
 * Created by jameson on 8/30/16.
 * changed by 二精-霁雪清虹 on 2017/11/19
 */
public class BannerScaleHelper {
    private BannerRecyclerView mRecyclerView;
    private Context mContext;

    private float mScale = 0.9f; // 两边视图scale
    private int mPagePadding = BannerAdapterHelper.sPagePadding; // 卡片的padding, 卡片间的距离等于2倍的mPagePadding
    private int mShowLeftCardWidth = BannerAdapterHelper.sShowLeftCardWidth;   // 左边卡片显示大小

    private int mCardWidth; // 卡片宽度
    private int mOnePageWidth; // 滑动一页的距离
    private int mCardGalleryWidth;

    private int mFirstItemPos;
    private int mCurrentItemOffset;

    private CardLinearSnapHelper mLinearSnapHelper = new CardLinearSnapHelper();
    private int mLastPos;

    public void attachToRecyclerView(final BannerRecyclerView mRecyclerView) {
        if (mRecyclerView == null) {
            return;
        }
        this.mRecyclerView = mRecyclerView;
        mContext = mRecyclerView.getContext();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // Log.e("TAG", "RecyclerView.OnScrollListener onScrollStateChanged");
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mLinearSnapHelper.mNoNeedToScroll = getCurrentItem() == 0 ||
                            getCurrentItem() == mRecyclerView.getAdapter().getItemCount() - 2;
                    if (mLinearSnapHelper.finalSnapDistance[0] == 0
                            && mLinearSnapHelper.finalSnapDistance[1] == 0) {
                        mCurrentItemOffset = 0;
                        mLastPos = getCurrentItem();
                        //认为是一次滑动停止 这里可以写滑动停止回调
                        mRecyclerView.dispatchOnPageSelected(mLastPos);
                        //Log.e("TAG", "滑动停止后最终位置为" + getCurrentItem());
                    }
                } else {
                    mLinearSnapHelper.mNoNeedToScroll = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //Log.e("TAG", String.format("onScrolled dx=%s, dy=%s", dx, dy));
                super.onScrolled(recyclerView, dx, dy);
                // dx>0则表示右滑, dx<0表示左滑, dy<0表示上滑, dy>0表示下滑
                mCurrentItemOffset += dx;
                onScrolledChangedCallback();
            }
        });
        initWidth();
        mLinearSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * 初始化卡片宽度
     */
    private void initWidth() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mCardGalleryWidth = mRecyclerView.getWidth();
                mCardWidth = mCardGalleryWidth - ScreenUtil.dip2px(mContext, 2 * (mPagePadding + mShowLeftCardWidth));
                mOnePageWidth = mCardWidth;
                scrollToPosition(mFirstItemPos);
            }
        });
    }

    public void scrollToPosition(int pos) {
        if (mRecyclerView == null) {
            return;
        }
        //mRecyclerView.getLayoutManager()).scrollToPositionWithOffset 方法不会回调  RecyclerView.OnScrollListener 的onScrollStateChanged方法,是瞬间跳到指定位置
        //mRecyclerView.smoothScrollToPosition 方法会回调  RecyclerView.OnScrollListener 的onScrollStateChanged方法 并且是自动居中，有滚动过程的滑动到指定位置
        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).
                scrollToPositionWithOffset(pos,
                        ScreenUtil.dip2px(mContext, mPagePadding + mShowLeftCardWidth));
        mCurrentItemOffset = 0;
        mLastPos = pos;
        //认为是一次滑动停止 这里可以写滑动停止回调
        mRecyclerView.dispatchOnPageSelected(mLastPos);
        onScrolledChangedCallback();
    }

    public void setFirstItemPos(int firstItemPos) {
        this.mFirstItemPos = firstItemPos;
    }


    /**
     * RecyclerView位移事件监听, view大小随位移事件变化
     */
    private void onScrolledChangedCallback() {
        int currentItemPos = getCurrentItem();
        int offset = mCurrentItemOffset - (currentItemPos - mLastPos) * mOnePageWidth;
        float percent = (float) Math.max(Math.abs(offset) * 1.0 / mOnePageWidth, 0.0001);

        //Log.e("TAG",String.format("offset=%s, percent=%s", offset, percent));
        View leftView = null;
        View currentView;
        View rightView = null;
        if (currentItemPos > 0) {
            leftView = mRecyclerView.getLayoutManager().findViewByPosition(currentItemPos - 1);
        }
        currentView = mRecyclerView.getLayoutManager().findViewByPosition(currentItemPos);
        if (currentItemPos < mRecyclerView.getAdapter().getItemCount() - 1) {
            rightView = mRecyclerView.getLayoutManager().findViewByPosition(currentItemPos + 1);
        }

        if (leftView != null) {
            // y = (1 - mScale)x + mScale
            leftView.setScaleY((1 - mScale) * percent + mScale);
        }
        if (currentView != null) {
            // y = (mScale - 1)x + 1
            currentView.setScaleY((mScale - 1) * percent + 1);
        }
        if (rightView != null) {
            // y = (1 - mScale)x + mScale
            rightView.setScaleY((1 - mScale) * percent + mScale);
        }
    }

    private int getCurrentItem() {
        return ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition() - 1;
    }

    public void setScale(float scale) {
        mScale = scale;
    }

    public void setPagePadding(int pagePadding) {
        mPagePadding = pagePadding;
    }

    public void setShowLeftCardWidth(int showLeftCardWidth) {
        mShowLeftCardWidth = showLeftCardWidth;
    }

    /**
     * 防止卡片在第一页和最后一页因无法"居中"而一直循环调用onScrollStateChanged-->SnapHelper.snapToTargetExistingView-->onScrollStateChanged
     * Created by jameson on 9/3/16.
     */
    private static class CardLinearSnapHelper extends LinearSnapHelper {
        public boolean mNoNeedToScroll = false;
        public int[] finalSnapDistance = {0, 0};

        @Override
        public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
            //Log.e("TAG", "calculateDistanceToFinalSnap");
            if (mNoNeedToScroll) {
                finalSnapDistance[0] = 0;
                finalSnapDistance[1] = 0;
            } else {
                finalSnapDistance = super.calculateDistanceToFinalSnap(layoutManager, targetView);
            }
            return finalSnapDistance;
        }
    }
}

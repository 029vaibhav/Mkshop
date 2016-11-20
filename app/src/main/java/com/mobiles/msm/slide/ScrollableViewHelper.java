package com.mobiles.msm.slide;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * Created by vaibhav on 21/4/16.
 */
public class ScrollableViewHelper {

    public int getScrollableViewScrollPosition(View scrollableView, boolean isSlidingUp) {
        if (scrollableView == null) return 0;
        if (scrollableView instanceof ScrollView) {
            if (isSlidingUp) {
                return scrollableView.getScrollY();
            } else {
                ScrollView sv = ((ScrollView) scrollableView);
                View child = sv.getChildAt(0);
                return (child.getBottom() - (sv.getHeight() + sv.getScrollY()));
            }
        } else if (scrollableView instanceof ListView && ((ListView) scrollableView).getChildCount() > 0) {
            ListView lv = ((ListView) scrollableView);
            if (lv.getAdapter() == null) return 0;
            if (isSlidingUp) {
                View firstChild = lv.getChildAt(0);
                // Approximate the scroll position based on the top child and the first visible item
                return lv.getFirstVisiblePosition() * firstChild.getHeight() - firstChild.getTop();
            } else {
                View lastChild = lv.getChildAt(lv.getChildCount() - 1);
                // Approximate the scroll position based on the bottom child and the last visible item
                return (lv.getAdapter().getCount() - lv.getLastVisiblePosition() - 1) * lastChild.getHeight() + lastChild.getBottom() - lv.getBottom();
            }
        } else if (scrollableView instanceof RecyclerView && ((RecyclerView) scrollableView).getChildCount() > 0) {
            RecyclerView rv = ((RecyclerView) scrollableView);
            RecyclerView.LayoutManager lm = rv.getLayoutManager();
            if (rv.getAdapter() == null) return 0;
            if (isSlidingUp) {
                View firstChild = rv.getChildAt(0);
                // Approximate the scroll position based on the top child and the first visible item
                return rv.getChildLayoutPosition(firstChild) * lm.getDecoratedMeasuredHeight(firstChild) - lm.getDecoratedTop(firstChild);
            } else {
                View lastChild = rv.getChildAt(rv.getChildCount() - 1);
                // Approximate the scroll position based on the bottom child and the last visible item
                return (rv.getAdapter().getItemCount() - 1) * lm.getDecoratedMeasuredHeight(lastChild) + lm.getDecoratedBottom(lastChild) - rv.getBottom();
            }
        } else {
            return 0;
        }
    }

}
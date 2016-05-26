package me.codpoe.onlyweather.ui.others;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by Codpoe on 2016/5/25.
 */

/**
 * 解决 SwipeRefreshLayout 和 ViewPager 的嵌套滑动冲突
 */
public class CodpoeSwipeRefreshLayout extends SwipeRefreshLayout {

    private int mTouchSlop;
    private float mPrevX;

    public CodpoeSwipeRefreshLayout(Context context, AttributeSet attrs) {

        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(event).getX();
                break;

            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);

                if (xDiff > mTouchSlop) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(event);
    }
}

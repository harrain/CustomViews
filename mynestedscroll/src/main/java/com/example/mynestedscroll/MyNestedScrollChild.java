package com.example.mynestedscroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.annotation.Size;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.support.v4.view.NestedScrollingChild;

/**
 * Created by data on 2017/11/22.
 */

public class MyNestedScrollChild extends LinearLayout implements NestedScrollingChild {
    /** 初始化helper，并将其方法覆盖在child接口方法内*/
    private NestedScrollingChildHelper mHelper;
    private int mChildHeight;
    private int[] consumed = new int[2];
    private int[] offsetInWindow = new int[2];
    private int lastY;

    public MyNestedScrollChild(Context context) {
        this(context,null);
    }

    public MyNestedScrollChild(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHelper = new NestedScrollingChildHelper(this);
        mHelper.setNestedScrollingEnabled(true);
    }

    /** 测量获得child容器高度，内有TextView，文字多情况下，需要两次测量*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //第一次测量，因为布局文件中高度是wrap_content，因此测量模式为ATMOST，即高度不能超过父控件的剩余空间
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mChildHeight = getMeasuredHeight();
        //第二次测量，对高度没有任何限制，那么测量出来的就是完全展示内容所需要的高度
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /** 触摸事件处理，获取滑动距离，找到嵌套滚动父布局并已消费距离的情况处理，和相反情况处理*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) event.getRawY();
                int dy = y - lastY;
                lastY = y;
                if (startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL) //如果找到了支持嵌套滚动的父类
                        && dispatchNestedPreScroll(0,dy,consumed,offsetInWindow)){//父类进行了一部分滚动
                    //这里的dy和parent消费的距离是一样的，remain是0
                    int remain = consumed[1] - dy;//获取滚动的剩余距离
                    if (remain!=0) scrollBy(0,remain);
                }else {
                    scrollBy(0,-dy);//当parent没滚的时候，child滚
                }
        }
        return true;
    }

    /** 限制滚动范围*/
    @Override
    public void scrollTo(@Px int x, @Px int y) {
        int maxY = getMeasuredHeight() - mChildHeight;
        if (y<0) y=0;
        if (y > maxY) y= maxY;
        super.scrollTo(x, y);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mHelper.startNestedScroll(axes);
    }

    @Override
    public boolean isNestedScrollingEnabled() {

        return mHelper.isNestedScrollingEnabled();
    }

    @Override
    public void stopNestedScroll() {
        mHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable @Size(value = 2) int[] consumed, @Nullable @Size(value = 2) int[] offsetInWindow) {
        return mHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable @Size(value = 2) int[] offsetInWindow) {
        return mHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }
}

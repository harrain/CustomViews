package com.example.mynestedscroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by data on 2017/11/22.
 */

public class MyNestedScrollParent extends LinearLayout implements NestedScrollingParent {
    /** 初始化helper，并将里面的方法覆盖在parent接口方法内*/
    private NestedScrollingParentHelper mHelper;
    private int imgHeight;
    private MyNestedScrollChild mChild;
    private String tag = "MyNestedScrollParent";

    public MyNestedScrollParent(Context context) {
        this(context,null);
    }

    public MyNestedScrollParent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHelper = new NestedScrollingParentHelper(this);
    }

    /**获得子view，及关键子view用来评判的高度*/
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        /** 通过getChildAt方法获得子view*/
        final ImageView imageview = (ImageView) getChildAt(0);
        mChild = (MyNestedScrollChild) getChildAt(2);
        /** finishInflate之后，监听viewtree得到初始的高度*/
        imageview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (imgHeight <= 0){
                    imgHeight = imageview.getMeasuredHeight();//控件的高不能搞错
                    Log.e(tag,"[onFinishInflate]  imgheight___"+imgHeight);
                }
            }
        });
    }

    /** 判断子view类型，确定是否开始嵌套滚动*/
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        if (target instanceof MyNestedScrollChild){
            Log.e(tag,"target is child   onStartNestedScroll");
            /** 2.关键参数target是自定义child，即开始嵌套滑动*/
            return true;
        }
        return false;
    }

    /** 嵌套滚动具体条件，parent先于滚动条件*/
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.e(tag,"[onNestedPreScroll]  dy___"+dy);
        // 下拉上划dy的值正负要实际调试
        /** 先于child滚动，即parent滚动，child的getScrollY不变*/
        if (showImg(dy) || hideImg(dy)){

            /** 确定显示和隐藏图片的条件，即上拉距离<imggeview高度，滚动隐藏；下拉child滚完再parent滚*/
            scrollBy(0,-dy);
            consumed[1] = dy;//告诉child我消费了多少
        }
    }

    private boolean showImg(int dy){
        if (dy>0){
            if (getScrollY() > 0 && mChild.getScrollY() == 0){
                /** 只有getScrollY()的判断情况下，child下拉时，imageview也会被拉下来
                 *加上child的getScrollY()==0的判断时，child先下滑，之后imageview才拉下来
                 *getScrollY() : return 该view的滚动顶部位置
                 */
                return true;
            }
        }
        return false;
    }

    private boolean hideImg(int dy){
        if (dy<0){
            if (getScrollY() < imgHeight) {
                /** 只消费imgHeight的距离，当滑动getScrollY超了，拒绝滚动，不再消费距离*/
                Log.e(tag,"[hideImg]    getScrollY___"+getScrollY());
                return true;
            }
        }
        return false;
    }

    /**
     * scrollBy内部会调用scrollTo
     * 限制滚动范围*/
    @Override
    public void scrollTo(@Px int x, @Px int y) {
        if (y < 0) y = 0;
        if (y > imgHeight) y = imgHeight;
        super.scrollTo(x, y);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        mHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
       mHelper.onStopNestedScroll(child);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return mHelper.getNestedScrollAxes();
    }
}

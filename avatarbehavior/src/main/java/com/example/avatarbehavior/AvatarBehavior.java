package com.example.avatarbehavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by net on 2017/11/30.
 */

public class AvatarBehavior extends CoordinatorLayout.Behavior<CircleImageView> {
    private float mCustomFinalHeight;
    private float mStartX;
    private float mFinalX;
    private float mStartY;
    private float mFinalY;
    private int mStartCircleHeight;
    private int mMaxDistance;
    private Context mContext;
    private String TAG = "AvatarBehavior";

    public AvatarBehavior() {

    }

    public AvatarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.AvatarImageBehavior);
            mCustomFinalHeight = a.getDimension(R.styleable.AvatarImageBehavior_finalHeight,0);
            a.recycle();
            Log.e(TAG, "constructor ...");
        }
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        return dependency instanceof android.support.v7.widget.Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {
        //初始数据配置
        initProperties(child,dependency);


        //计算Toolbar移动百分比系数，可以是长度/长度 or 纵坐标/纵坐标. 显然方法参数里没有dy，这里用坐标比较好
        float toolbarMoveFactor = (dependency.getY() - 0)/mMaxDistance;//每次移动的距边界距离比  比例系数不能用int,要用float

        //最重要的是确定toolbar纵坐标减小。circleImageView移动时横纵坐标、circleImageview的宽高 之间的关系
        setCircleLayoutParams(toolbarMoveFactor,child);
        setCircleXY(toolbarMoveFactor,child,dependency);//确定每次移动CicleImageView的坐标和宽高变化

        Log.e(TAG, "onDependentViewChanged: toolbar Y___"+dependency.getY()+"===maxDistance___"+mMaxDistance);
        Log.e(TAG, "onDependentViewChanged: toolbar factor___"+toolbarMoveFactor);
        Log.e(TAG, "onDependentViewChanged: circle X___"+child.getX()+"---circle Y___"+child.getY());
        return true;
    }

    private void setCircleXY(float toolbarMoveFactor, CircleImageView child, View dependency) {

        //纵坐标 (Y - mFinalY) / (mStartY - mFinalY) = (dependency.getY() - 0 ) / mMaxDistance ;根据这一关系，计算出circleview的Y坐标，circleview和toolbar同步yidong
        float childY = toolbarMoveFactor * (mStartY - mFinalY) + mFinalY;
        /** x坐标分为两段处理，一是垂直上移，居中，二是水平向左 */
        float childX = 0;
        if (toolbarMoveFactor >= 0.7){
            childX = dependency.getWidth()/2 - child.getWidth()/2;//居中上移
            mStartX = childX;//获取最后一次垂直上移的横坐标作为接下来向左平移的起点
        }else {
            //同理，横坐标 (X - mFinalX) / (startX - mFinalX ) = dependency.getY()-0 / maxDistance*0.7 = toolbarMOveFactor *10/7
            childX = toolbarMoveFactor*10/7 * (mStartX - mFinalX) + mFinalX;
        }
        child.setX(childX);
        child.setY(childY);
    }

    private void setCircleLayoutParams(float toolbarMoveFactor, CircleImageView child){
        //同理，cireleImageView的宽高：
        //高：(height - mCustomFinalHeight ) / (mStartCircleHeight - mCustomFinalHeight) = toobarMoveFactor
        int childWidth,childHeight;
        childHeight = (int) (toolbarMoveFactor * (mStartCircleHeight - mCustomFinalHeight) +mCustomFinalHeight);
        //宽 = 高，因为是CircleImageView
        childWidth = childHeight;
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        lp.width = childWidth;
        lp.height = childHeight;
        child.setLayoutParams(lp);

    }


    private void initProperties(CircleImageView child, View dependency) {
        //起点、终点横坐标
        if (mStartX == 0){
            mStartX =  child.getX();
        }
        if (mFinalX == 0){
            mFinalX = dp2px(16);
        }
        //起终点纵坐标
        if (mStartY == 0){
            mStartY =  dependency.getY() - child.getHeight()/2;
        }
        if (mFinalY == 0){
            mFinalY = (dependency.getHeight()/2 - mCustomFinalHeight/2);//让circleImageView居于toolbar中央
        }
        //circleImagView初始高度
        if (mStartCircleHeight == 0){
            mStartCircleHeight = child.getHeight();
        }
        //获取移动总长
        if (mMaxDistance == 0){
            mMaxDistance = (int) (dependency.getY() - 0);
            Log.e(TAG,"maxDistance___"+mMaxDistance);
        }
    }

    private int dp2px(int dpValue){

        float density = (int) mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density +0.5f);
    }
}

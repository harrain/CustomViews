package com.example.avatarbehavior;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private Toolbar toolbar;
    private LinearLayout mTitleContainer;
    private TextView mTitleTv;
    private boolean mIsToolbarTitleVisible = true;
    private boolean mIsTitleContainerVisible = true;
    private String tag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
//        setSupportActionBar(toolbar);//这样设置出现了意想不到的结果
        mTitleContainer = (LinearLayout) findViewById(R.id.main_ll_title);
        mTitleTv = (TextView) findViewById(R.id.main_textview_title);

        appBarLayout.addOnOffsetChangedListener(this);
        toolbar.inflateMenu(R.menu.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();//获取最大滚动距离
        /** verticalOffset 对该垂直方向的位移取绝对值，计算滚动比 */
        float factor = (float) Math.abs(verticalOffset) / maxScroll;//计算系数，被除数必须是float型，结果必须是float型
        titleToolbarAlpha(factor);//处理toolbar的title隐藏和显示
        titleContainerAlpha(factor);//处理头像下面的名字介绍隐藏和显示
        Log.e(tag,"onOffset: verticalOffset___"+verticalOffset+"---maxScroll___"+maxScroll);
        Log.e(tag,"onOffset: factor___"+factor);
    }

    private void titleToolbarAlpha(float factor) {
        //toolbar接近屏幕顶端时显示toolbar的title
        if (factor >= 0.9){
            if (mIsToolbarTitleVisible){
                startAlphaAnimation(mTitleTv,200, View.VISIBLE);
                mIsToolbarTitleVisible = false;
            }
        }else if (!mIsToolbarTitleVisible){
            startAlphaAnimation(mTitleTv,200,View.INVISIBLE);
            mIsToolbarTitleVisible = true;
        }
    }

    private void titleContainerAlpha(float factor){
        //0.3为估算值，整体垂直上移0.3的比例，名字和介绍隐藏
        if (factor >= 0.3){
            if (mIsTitleContainerVisible){
                startAlphaAnimation(mTitleContainer,200,View.INVISIBLE);
                mIsTitleContainerVisible = false;
            }
        }else if (!mIsTitleContainerVisible){
            startAlphaAnimation(mTitleContainer,200, View.VISIBLE);
            mIsTitleContainerVisible = true;
        }
    }

    private void startAlphaAnimation(View view, int duration, int visibility){
        view.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = visibility == View.VISIBLE ?
                new AlphaAnimation(0f,1f) : new AlphaAnimation(1f,0f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);//动画结束后保持状态
        view.startAnimation(alphaAnimation);
    }
}


package com.gastudio.leafloading;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class LeafLoadingActivity extends Activity implements OnSeekBarChangeListener,
        OnClickListener {

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_PROGRESS:
                    if (mProgress < 40) {
//                        mProgress += 1;//progress 加1
//                        // 随机800ms以内刷新一次
//                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,
//                                100);//发送延时信息启动下一次
                        mLeafLoadingView.setProgress(mProgress);//设置prograss，刷新
                    } else {
//                        mProgress += 1;
//                        // 随机1200ms以内刷新一次
//                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,
//                                100);
                        mLeafLoadingView.setProgress(mProgress);

                    }
                    break;

                default:
                    break;
            }
        };
    };

    private static final int REFRESH_PROGRESS = 0x10;
    private LeafLoadingView mLeafLoadingView;
    private SeekBar mAmpireSeekBar;
    private SeekBar mDistanceSeekBar;
    private TextView mMplitudeText;
    private TextView mDisparityText;
    private View mFanView;
    private Button mClearButton;
    private int mProgress = 0;

    private TextView mProgressText;
    private View mAddProgress;
    private SeekBar mFloatTimeSeekBar;

    private SeekBar mRotateTimeSeekBar;
    private TextView mFloatTimeText;
    private TextView mRotateTimeText;
    private MainBroadCastReceiver mainBroadCastReceiver;
    private TextView mFanTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaf_loading_layout);
        initViews();
        mainBroadCastReceiver = new MainBroadCastReceiver();
        registerReceiver(mainBroadCastReceiver,new IntentFilter("com.gastudio.leafloading.stopInvalidate"));
        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, 3000);//导火索，发送引信，启动刷新
    }

    private void initViews() {
        mFanView = findViewById(R.id.fan_pic);
        RotateAnimation rotateAnimation = AnimationUtils.initRotateAnimation(false, 1500, true,
                Animation.INFINITE);//初始化旋转动画，逆时针，持续1500ms，一直转动
        mFanView.startAnimation(rotateAnimation);//启动View动画，旋转风扇
        mClearButton = (Button) findViewById(R.id.clear_progress);
        mClearButton.setOnClickListener(this);

        mLeafLoadingView = (LeafLoadingView) findViewById(R.id.leaf_loading);
        mMplitudeText = (TextView) findViewById(R.id.text_ampair);
        mMplitudeText.setText(getString(R.string.current_mplitude,
                mLeafLoadingView.getMiddleAmplitude()));

        mDisparityText = (TextView) findViewById(R.id.text_disparity);
        mDisparityText.setText(getString(R.string.current_Disparity,
                mLeafLoadingView.getMplitudeDisparity()));

        mAmpireSeekBar = (SeekBar) findViewById(R.id.seekBar_ampair);
        mAmpireSeekBar.setOnSeekBarChangeListener(this);
        mAmpireSeekBar.setProgress(mLeafLoadingView.getMiddleAmplitude());
        mAmpireSeekBar.setMax(50);

        mDistanceSeekBar = (SeekBar) findViewById(R.id.seekBar_distance);
        mDistanceSeekBar.setOnSeekBarChangeListener(this);
        mDistanceSeekBar.setProgress(mLeafLoadingView.getMplitudeDisparity());
        mDistanceSeekBar.setMax(20);

        mAddProgress = findViewById(R.id.add_progress);
        mAddProgress.setOnClickListener(this);
        mProgressText = (TextView) findViewById(R.id.text_progress);

        mFloatTimeText = (TextView) findViewById(R.id.text_float_time);
        mFloatTimeSeekBar = (SeekBar) findViewById(R.id.seekBar_float_time);
        mFloatTimeSeekBar.setOnSeekBarChangeListener(this);
        mFloatTimeSeekBar.setMax(5000);
        mFloatTimeSeekBar.setProgress((int) mLeafLoadingView.getLeafFloatTime());
        mFloatTimeText.setText(getResources().getString(R.string.current_float_time,
                mLeafLoadingView.getLeafFloatTime()));

        mRotateTimeText = (TextView) findViewById(R.id.text_rotate_time);
        mRotateTimeSeekBar = (SeekBar) findViewById(R.id.seekBar_rotate_time);
        mRotateTimeSeekBar.setOnSeekBarChangeListener(this);
        mRotateTimeSeekBar.setMax(5000);
        mRotateTimeSeekBar.setProgress((int) mLeafLoadingView.getLeafRotateTime());
        mRotateTimeText.setText(getResources().getString(R.string.current_float_time,
                mLeafLoadingView.getLeafRotateTime()));

        mFanTv = (TextView) findViewById(R.id.fan_complete);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == mAmpireSeekBar) {
            mLeafLoadingView.setMiddleAmplitude(progress);
            mMplitudeText.setText(getString(R.string.current_mplitude,
                    progress));
        } else if (seekBar == mDistanceSeekBar) {
            mLeafLoadingView.setMplitudeDisparity(progress);
            mDisparityText.setText(getString(R.string.current_Disparity,
                    progress));
        } else if (seekBar == mFloatTimeSeekBar) {
            mLeafLoadingView.setLeafFloatTime(progress);
            mFloatTimeText.setText(getResources().getString(R.string.current_float_time,
                    progress));
        }
        else if (seekBar == mRotateTimeSeekBar) {
            mLeafLoadingView.setLeafRotateTime(progress);
            mRotateTimeText.setText(getResources().getString(R.string.current_rotate_time,
                    progress));
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        if (v == mClearButton) {
            mLeafLoadingView.setProgress(0);
            mHandler.removeCallbacksAndMessages(null);
            mProgress = 0;
        } else if (v == mAddProgress) {
            mProgress++;
            mLeafLoadingView.setProgress(mProgress);
            mProgressText.setText(String.valueOf(mProgress));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            unregisterReceiver(mainBroadCastReceiver);
        }catch(Exception e){
            e.printStackTrace();
        }
        finish();
    }

    private class MainBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            mLeafLoadingView.setStopInvalidate(true);
            mHandler.removeCallbacksAndMessages(null);

            AnimatorSet set = new AnimatorSet();
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(mFanView,"ScaleX",1,0);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(mFanView,"ScaleY",1,0);
            set.play(scaleX).with(scaleY);
            set.setDuration(900);
            set.setInterpolator(new DecelerateInterpolator());
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mFanView.setVisibility(View.INVISIBLE);
                    mFanTv.setVisibility(View.VISIBLE);
                    AnimatorSet set = new AnimatorSet();
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(mFanTv,"ScaleX",0,1);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(mFanTv,"ScaleY",0,1);
                    set.play(scaleX).with(scaleY);
                    set.setDuration(800);
                    set.setInterpolator(new AccelerateInterpolator());
                    set.start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            set.start();
//            ScaleAnimation scaleAnimation = new ScaleAnimation(1,0,1,0);
//            scaleAnimation.setDuration(2000);
//
//            if (mFanView!=null)mFanView.startAnimation(scaleAnimation);
        }
    }
}

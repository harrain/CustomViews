package com.example.huazhewan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlayActivity extends AppCompatActivity {

    private A a;
    private B b;
    private int _AxDelta;
    private int _BxDelta;
    private int _AyDelta;
    private int _ByDelta;
    private String tag = "PlayActivity";
    RelativeLayout rl;
    private TextView atv;
    private TextView btv;
    TextView timeTv;
    long startTime;
    private Disposable mDisposable;

    int AoldX,AoldY;
    int BoldX,BoldY;
    private Observable observable;
    private int maxW;
    private int maxY;
    private int[] rlLocation = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity_main);
        rl = (RelativeLayout) findViewById(R.id.rl);
        a = (A) findViewById(R.id.a);
        b = (B) findViewById(R.id.b);
        atv = (TextView) findViewById(R.id.axy_tv);
        btv = (TextView) findViewById(R.id.bxy_tv);

        timeTv = (TextView) findViewById(R.id.time_tv);
        huaA();
        huaB();

        maxW = getResources().getDisplayMetrics().widthPixels;
        maxY = getResources().getDisplayMetrics().heightPixels;

        observable = Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.computation());
        countTime();

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);
        Log.d(tag, "onWindowFocusChanged: "+hasFocus);
        if (hasFocus)
        rl.getLocationOnScreen(rlLocation);
        Log.d(tag, "onWindowFocusChanged: rllocation : "+rlLocation[0]+" and "+rlLocation[1]);
    }

    private void chongying(){
        a.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        layoutParams.leftMargin = (int) event.getX();
                        layoutParams.topMargin = (int) event.getY();
                        v.setLayoutParams(layoutParams);
                        break;
                }
                return true;
            }
        });
    }

    private void huaA(){
        a.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                int maxlm = maxW - v.getWidth() ;
                int maxtm = maxY - v.getHeight()- rlLocation[1];

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        Log.d(tag, "ACTION_DOWN: leftMargin is "+lParams.leftMargin+" and topMargin is "+lParams.topMargin);
                        _AxDelta = X - lParams.leftMargin;
                        _AyDelta = Y - lParams.topMargin;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        atv.setText("A x:"+X+"  y:"+Y);
                        int lm = X - _AxDelta;
                        int tm = Y - _AyDelta;
                        //会出现一个影子，闪动
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
//                        layoutParams.leftMargin = (int) event.getX();
//                        layoutParams.topMargin = (int) event.getY();
                        layoutParams.leftMargin = lm;
                        if (lm > maxlm) layoutParams.leftMargin = maxlm;
                        if (lm < 0) layoutParams.leftMargin = 0;

                        layoutParams.topMargin = tm;
                        if (tm > maxtm) layoutParams.topMargin = maxtm;
                        if (tm < 0) layoutParams.topMargin = 0;
                        v.setLayoutParams(layoutParams);
//                        v.setX(event.getX());
//                        v.setY(event.getY());//会出现一个影子，闪动

                        if (Math.abs(X - AoldX) < 1 && Math.abs(Y - AoldY) < 1) {
                            flag = false;
                            return true;
                        }
                        AoldX = X; AoldY = Y;
                        flag = true;
                        Log.d(tag, "onTouch: raw x is "+X + " and y is "+Y);
                        Log.d(tag, "ACTION_MOVE: leftMargin is "+layoutParams.leftMargin+" and topMargin is "+layoutParams.topMargin);
                        Log.d(tag, "ACTION_MOVE: getx is "+event.getX()+" and gety is "+event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        flag = false;
                        break;
                }

                return true;
            }
        });
    }

    private void huaB(){
        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                 int X = (int) event.getRawX();
                 int Y = (int) event.getRawY();
                int maxlm = maxW - v.getWidth() ;
                int maxtm = (int) (maxY - v.getHeight()- rlLocation[1]);
                Log.d(tag, "onTouch: rl Y is "+rlLocation[1]);

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        Log.d(tag, "b ACTION_DOWN: leftMargin is "+lParams.leftMargin+" and topMargin is "+lParams.topMargin);
                        _BxDelta = X - lParams.leftMargin;
                        _ByDelta = Y - lParams.topMargin;
//                        bm = Y - lParams.bottomMargin;
//                        rm  = X - lParams.rightMargin;
                        break;
                    case MotionEvent.ACTION_MOVE:

//
                        btv.setText("B x:"+X+"  y:"+Y);
                        int lm = X - _BxDelta;
                        int tm = Y - _ByDelta;

                        //会出现一个影子，闪动
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        layoutParams.leftMargin = lm;
                        if (lm > maxlm) layoutParams.leftMargin = maxlm;
                        if (lm < 0) layoutParams.leftMargin = 0;

                        layoutParams.topMargin = tm;
                        if (tm > maxtm) layoutParams.topMargin = maxtm;
                        if (tm < 0) layoutParams.topMargin = 0;

                        v.setLayoutParams(layoutParams);

                        if (Math.abs(X - BoldX) < 1 && Math.abs(Y - BoldY) < 1) {
                            flag = false;
                            return true;
                        }
                        Log.d(tag, "onTouch: maxW is "+maxW +" and maxY is "+maxY);
                        Log.d(tag, "onTouch: getWidth is "+v.getWidth() +" and getHeight is "+v.getHeight());
//                        if (X >= maxW - _BxDelta) {
//                            RelativeLayout.LayoutParams lpx = (RelativeLayout.LayoutParams) v.getLayoutParams();
//                            layoutParams.leftMargin = maxW - _BxDelta;
//                            v.setLayoutParams(lpx);
//                        }
//                        if (Y >= maxY - _ByDelta) {
//                            RelativeLayout.LayoutParams lpy = (RelativeLayout.LayoutParams) v.getLayoutParams();
//                            layoutParams.bottomMargin = maxY - _ByDelta;
//                            v.setLayoutParams(lpy);
//                        }
                        BoldX = X; BoldY = Y;
                        flag = true;
                        Log.d(tag, "b onTouch: raw x is "+X + " and y is "+Y);
                        Log.d(tag, "b ACTION_MOVE: leftMargin is "+layoutParams.leftMargin+" and topMargin is "+layoutParams.topMargin);
                        Log.d(tag, "b ACTION_MOVE: getx is "+event.getX()+" and gety is "+event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        flag = false;
                        break;
                }

                return true;
            }
        });
    }

    private void b(){
        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        Log.d(tag, "ACTION_DOWN: leftMargin is "+lParams.leftMargin+" and topMargin is "+lParams.topMargin);
                        _AxDelta = X - lParams.leftMargin;
                        _AyDelta = Y - lParams.topMargin;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //会出现一个影子，闪动
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
//                        layoutParams.leftMargin = (int) event.getX();
//                        layoutParams.topMargin = (int) event.getY();
                        layoutParams.leftMargin = X - _AxDelta;
                        layoutParams.topMargin = Y - _AyDelta;
                        v.setLayoutParams(layoutParams);
//                        v.setX(event.getX());
//                        v.setY(event.getY());//会出现一个影子，闪动
                        btv.setText("B x:"+X+"  y:"+Y);

                        if (Math.abs(X - AoldX) < 1 && Math.abs(Y - AoldY) < 1) {
                            flag = false;
                            return true;
                        }
                        AoldX = X; AoldY = Y;
                        flag = true;
                        Log.d(tag, "onTouch: raw x is "+X + " and y is "+Y);
                        Log.d(tag, "ACTION_MOVE: leftMargin is "+layoutParams.leftMargin+" and topMargin is "+layoutParams.topMargin);
                        Log.d(tag, "ACTION_MOVE: getx is "+event.getX()+" and gety is "+event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        flag = false;
                        break;
                }

                return true;
            }
        });
    }



    private void countTime(){
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }


    private boolean flag = false;
    Observer observer = new Observer<Long>() {
        @Override
        public void onSubscribe(Disposable d) {
            mDisposable = d;
        }

        @Override
        public void onNext(Long value) {
//            Log.d(tag, "onNext: starttime is "+startTime);

            long aLong = startTime;
            String minu = null;
            String sec = null;
            int a = (int) (aLong / 60);
            if (a < 10) minu = "0" + a;
            else if (a>=60) {
                int h = a / 60;
                if (h < 10) minu = "0"+h+":";
                else minu = h+":";
                int p = a%60;
                if (p < 10) minu += "0"+p;
                else minu += p;
            }
            else minu = a+"";

            int b = (int) (aLong % 60);
            if (b < 10) sec = "0" + b;
            else sec = b+"";
            if (flag) {
                timeTv.setText(minu + ":" + sec);
                startTime++;
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };
}

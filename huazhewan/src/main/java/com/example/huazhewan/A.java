package com.example.huazhewan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by net on 2017/12/19.
 */

public class A extends View {

    int vWidth,vHeight;
    Paint circlePaint;
    Paint textPaint;
    private String tag = "A";

    public A(Context context) {
        this(context,null);
    }

    public A(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        circlePaint = new Paint();
        circlePaint.setStrokeWidth(15);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.GRAY);

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.DKGRAY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        vWidth = w;
        vHeight = h;
        Log.d(tag, "onSizeChanged: width "+vWidth+" and height "+vHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
//        Log.d(tag, "onDraw: width "+w+" and height "+h);
        canvas.translate(vWidth/2,vHeight/2);
        canvas.drawCircle(0,0,vWidth/2-10,circlePaint);

        canvas.drawText("A",-12,12,textPaint);

        Log.d(tag, "onDraw: A");
    }

}

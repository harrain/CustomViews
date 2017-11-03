package com.example.pieview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by data on 2017/11/1.
 */

public class CanvasTestView extends View {

    private Paint mPaint;

    public CanvasTestView(Context context) {
        this(context,null);
    }

    public CanvasTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanvasTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);//设置颜色
        mPaint.setStyle(Paint.Style.FILL);//设置绘制方式
        mPaint.setStrokeWidth(10);//设置线宽
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLUE);
        canvas.drawPoint(200,200,mPaint);
        canvas.drawPoints(new float[]{
                50,600,
                50,700,
                50,800
        },mPaint);

        canvas.drawLine(50,40,100,100,mPaint);
        canvas.drawLines(new float[]{
                50,100,200,100,
                50,200,200,200
        },mPaint);

        RectF rectF = new RectF(230,200,400,350);
        canvas.drawRect(rectF,mPaint);

        RectF r = new RectF(230,380,400,500);
        canvas.drawRoundRect(r,50,50,mPaint);

        RectF o = new RectF(230,520,400,600);
        canvas.drawOval(o,mPaint);

        canvas.drawCircle(300,750,100,mPaint);

        RectF arc = new RectF(500,100,800,400);
        canvas.drawArc(arc,0,90,true,mPaint);

        RectF a = new RectF(500,500,800,800);
        canvas.drawArc(a,0,270,false,mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        RectF ra = new RectF(500,900,800,1200);
        canvas.drawArc(ra,180,180,false,mPaint);
    }
}

package com.example.pieview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by data on 2017/11/1.
 */

public class PieView extends View {
    // 颜色表
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private ArrayList<PieData> mPieList;
    private float mStartAngle = 0;
    private int mWidth;
    private int mHeight;

    // 文字色块部分
    private PointF mStartPoint = new PointF(20, 20);
    private PointF mCurrentPoint = new PointF(mStartPoint.x, mStartPoint.y);
    private float mColorRectSideLength = 20;
    private float mTextInterval = 10;
    private float mRowMaxLength;

    private Paint mPaint;

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPieList == null) return;

        canvas.translate(mWidth / 2, mHeight / 2);
        float radius = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        RectF rectf = new RectF(-radius, -radius, radius, radius);

        float currentAngle = 0;

        for (PieData pieData : mPieList) {
            mPaint.setColor(pieData.getColor());
            canvas.drawArc(rectf,currentAngle,pieData.getAngle(),true,mPaint);
            currentAngle += pieData.getAngle();

//            canvas.save();
//            canvas.translate(-mWidth / 2, -mHeight / 2);
//            RectF colorRect = new RectF(mCurrentPoint.x, mCurrentPoint.y, mCurrentPoint.x + mColorRectSideLength, mCurrentPoint.y + mColorRectSideLength);
//            canvas.restore();
            Log.i("currentAngle",currentAngle+"");
        }



    }

    public void setData(ArrayList<PieData> list) {
        mPieList = list;
        if (mPieList == null || mPieList.size() == 0) return;
        float sumValue = 0;
        for (PieData pieData : mPieList) {
            sumValue += pieData.getValue();
        }
        for (int i = 0; i < mPieList.size(); i++) {
            PieData pieData = mPieList.get(i);
            float percentage = pieData.getValue() / sumValue;
            float angle = percentage * 360;
            pieData.setPercentage(percentage);
            pieData.setAngle(angle);
            pieData.setColor(mColors[i % mColors.length]);

            Log.i("angle", "" + pieData.getAngle());
        }
        invalidate();
    }
}

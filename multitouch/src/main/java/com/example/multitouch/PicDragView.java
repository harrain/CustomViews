package com.example.multitouch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by net on 2017/12/22.
 */

public class PicDragView extends View {


    String TAG = "Gcs";

    Bitmap mBitmap;         // 图片
    RectF mBitmapRectF;     // 图片所在区域
    Matrix mBitmapMatrix;   // 控制图片的 matrix

    boolean canDrag = false;
    PointF lastPoint = new PointF(0, 0);
    Paint mDeafultPaint;

    public PicDragView(Context context) {
        this(context, null);
    }

    public PicDragView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDeafultPaint = new Paint();
        // 调整图片大小
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        mBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.two_element, options);
        mBitmapRectF = new RectF(0,0,mBitmap.getWidth(), mBitmap.getHeight());
        mBitmapMatrix = new Matrix();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // 判断按下位置是否包含在图片区域内
                if (mBitmapRectF.contains((int)event.getX(), (int)event.getY())){
                    canDrag = true;
                    lastPoint.set(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                canDrag = false;
            case MotionEvent.ACTION_MOVE:
                if (canDrag) {
                    // 移动图片
                    mBitmapMatrix.postTranslate(event.getX() - lastPoint.x, event.getY() - lastPoint.y);
                    // 更新上一次点位置
                    lastPoint.set(event.getX(), event.getY());

                    // 更新图片区域
                    mBitmapRectF = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
                    mBitmapMatrix.mapRect(mBitmapRectF);

                    invalidate();
                }
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mBitmapMatrix, mDeafultPaint);
    }


}

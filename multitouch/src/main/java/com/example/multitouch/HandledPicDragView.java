package com.example.multitouch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by net on 2017/12/22.
 */

public class HandledPicDragView extends View {

    Bitmap bitmap;
    Matrix bitmapMatrix;
    Paint bitmapPaint;
    boolean canDrag = false;
    RectF bitmapRectf;
    Point lastPoint;
    private String TAG = "HandledPicDragView";

    public HandledPicDragView(Context context) {
        this(context, null);
    }

    public HandledPicDragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = 480;
        options.outHeight = 400;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.two_element, options);

        bitmapMatrix = new Matrix();
        bitmapPaint = new Paint();

        bitmapRectf = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        lastPoint = new Point();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, bitmapMatrix, bitmapPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: ACTION_DOWN");
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(TAG, "onTouchEvent: ACTION_POINTER_DOWN");
                if (event.getPointerId(event.getActionIndex()) == 0 && bitmapRectf.contains(event.getX(), event.getY())) {
                    lastPoint.set((int) event.getX(), (int) event.getY());
                    canDrag = true;
                    Log.d(TAG, "onTouchEvent: ACTION_POINTER_DOWN canDrag = true");
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: ACTION_UP");
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(TAG, "onTouchEvent: ACTION_POINTER_UP");
                if (event.getPointerId(event.getActionIndex()) == 0) {
                    Log.d(TAG, "onTouchEvent: ACTION_POINTER_UP canDrag = false");
                    canDrag = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (canDrag){
                    Log.d(TAG, "onTouchEvent: ACTION_MOVE canDrag = true");
                    int index = event.findPointerIndex(0);
                    bitmapMatrix.postTranslate(event.getX(index)-lastPoint.x,event.getY(index)-lastPoint.y);
                    lastPoint.set((int)event.getX(index),(int)event.getY(index));
                    bitmapRectf = new RectF(0,0,bitmap.getWidth(),bitmap.getHeight());
                    bitmapMatrix.mapRect(bitmapRectf);
                    invalidate();
                }

        }

        return true;
    }
}

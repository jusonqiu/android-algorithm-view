package com.juson.view.algview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Qiu Zhaosheng (JusonQiu@gmail.com) on 4/21/16.
 */
public class AlgView extends View {

    private static final String NODATA = "No Data!";
    private int[] arr;
    private Paint mPaint;
    private int indexA;
    private int indexB;

    public void init(Context context) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff0000ff);
        arr = null;

    }

    public AlgView(Context context) {
        super(context);
        this.init(context);
    }

    public AlgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);

    }

    public void setArr(int[] arr, int a, int b) {
        this.arr = arr;
        this.indexA = a;
        this.indexB = b;

        invalidate();
    }

    private int getMax(int[] arr){
        int N = arr.length;
        int max = arr[0];
        for (int i = 1; i < N; i++) {
            if (max < arr[i]) max = arr[i];
        }
        return max;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (arr != null && arr.length > 0){
            int w = getWidth();
            int h = getHeight();

            int N = arr.length;
            float rect_w = w/(N+2);
            mPaint.setStrokeWidth(rect_w*0.8f);

            int max = getMax(arr);
            float per = h/max;

            float x = 0;
            float y = h;

            float[][] tmp = new float[2][2];
            int index = 0;

            for (int a : arr) {
                x += rect_w;
                canvas.drawLine(x, y, x, y-(a*per),  mPaint);
                if (index == this.indexA){
                    tmp[0][0] = x;
                    tmp[0][1] = y-(a*per);
                }
                if (index == this.indexB){
                    tmp[1][0] = x;
                    tmp[1][1] = y-(a*per);
                }
                index++;
            }
            if (indexA != indexB) {
                mPaint.setStrokeWidth(1.0f);
                mPaint.setColor(Color.RED);
                Path path = new Path();
                path.moveTo(tmp[0][0], tmp[0][1]);

                path.quadTo(
                        tmp[0][0]+Math.abs(tmp[1][0]-tmp[0][0])*2/3,
                        y - max*per,
                        tmp[1][0],
                        tmp[1][1]
                        );

                canvas.drawPath(path, mPaint);
                mPaint.setColor(Color.BLUE);
            }

        }else{
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(1.0f);
            mPaint.setColor(0xff0000ff);
            canvas.drawText(NODATA, getWidth()/2, getHeight()/2, mPaint);
        }

    }
}

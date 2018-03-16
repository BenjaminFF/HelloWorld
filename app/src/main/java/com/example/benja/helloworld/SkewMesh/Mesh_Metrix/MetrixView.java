package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by benja on 2018/3/15.
 */

public class MetrixView extends View {
    private int row,column;

    private int matrix[][];

    private Path path;

    private int mWidth,mHeight;

    private Paint paint,pathPaint;
    public MetrixView(Context context) {
        super(context);
    }

    public MetrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        paint.setColor(Color.BLACK);
        path=new Path();

        pathPaint=new Paint();
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setColor(Color.GREEN);
        pathPaint.setStrokeWidth(5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mHeight/2);
        if (mWidth>=mHeight){
            mWidth=mHeight;
        }
        canvas.translate(-mWidth/2+mWidth/10,-mWidth/2+mWidth/10);
        canvas.drawRect(0,0,mWidth*8/10,mWidth*8/10,paint);
        path=new Path();
        path.moveTo(-mWidth/20+mWidth/10,-mWidth/20);
        path.lineTo(-mWidth/20,-mWidth/20);
        path.lineTo(-mWidth/20,mWidth/20+mWidth*8/10);
        path.lineTo(-mWidth/20+mWidth/10,mWidth/20+mWidth*8/10);
        path.moveTo(mWidth*8/10+mWidth/20-mWidth/10,-mWidth/20);
        path.lineTo(mWidth*8/10+mWidth/20,-mWidth/20);
        path.lineTo(mWidth*8/10+mWidth/20,mWidth/20+mWidth*8/10);
        path.lineTo(mWidth*8/10+mWidth/20-mWidth/10,mWidth/20+mWidth*8/10);
        canvas.drawPath(path,pathPaint);
    }
}

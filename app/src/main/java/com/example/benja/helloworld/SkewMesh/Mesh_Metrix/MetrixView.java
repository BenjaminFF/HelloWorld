package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.benja.helloworld.R;

/**
 * Created by benja on 2018/3/15.
 */

public class MetrixView extends View {
    private int row,column;

    private int matrix[][];

    private Path path;

    private int mWidth,mHeight;

    private Paint paint,pathPaint,textPaint;

    private int cellWidth;   //cell为正方形 width=height

    private int mbasepointX,mbasePointY;

    private int mSquareWidth;

    private Paint.FontMetrics fontMetrics;

    private Rect targetRect;

    private int baselineY;   //画数字的基线Y坐标

    public MetrixView(Context context) {
        super(context);
    }

    public MetrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.MetrixView,0,0);
        row=a.getInteger(R.styleable.MetrixView_row,-1);
        column=a.getInteger(R.styleable.MetrixView_column,-1);

        paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        path=new Path();

        pathPaint=new Paint();
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setColor(Color.GREEN);
        pathPaint.setStrokeWidth(3);

        textPaint=new Paint();
        textPaint.setColor(Color.GREEN);

        targetRect=new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);

        if (mWidth>=mHeight){
            mSquareWidth=mHeight;     //省去多余的部分，让view变成一个正方形，并且居中
        }else {
            mSquareWidth=mWidth;
        }
        if (row>=column){
            cellWidth=mSquareWidth/row;   //加一格来画外框
        }else {
            cellWidth=mSquareWidth/column;
        }

        mbasepointX=mWidth/2-mSquareWidth/2;
        mbasePointY=mHeight/2-mSquareWidth/2;

        textPaint.setTextSize(50);
        fontMetrics=textPaint.getFontMetrics();
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.scale(0.8f,0.8f,mWidth/2,mHeight/2);
        canvas.drawRect(mbasepointX,mbasePointY,mbasepointX+mSquareWidth,mbasePointY+mSquareWidth,paint);

        drawOutlinePath(canvas);
        drawMetrixText(canvas);
    }

    private void drawOutlinePath(Canvas canvas){

    }

    private void drawMetrixText(Canvas canvas){

        canvas.save();
        int dx=(mbasepointX+mSquareWidth)/2-(mbasepointX+cellWidth*column)/2;
        int dy=(mbasePointY+mSquareWidth)/2-(mbasePointY+cellWidth*row)/2;
        canvas.translate(dx,dy);
        for (int i=0;i<column;i++)
            for(int j=0;j<row;j++){
                targetRect.left=mbasepointX+cellWidth*i;
                targetRect.right=mbasepointX+cellWidth*(i+1);
                targetRect.top=mbasePointY+cellWidth*j;
                targetRect.bottom=mbasePointY+cellWidth*(j+1);

                //(top+bottom)/2-bottom
                baselineY=(int)(targetRect.centerY()-(fontMetrics.top+fontMetrics.bottom)/2);
                canvas.drawText("5",targetRect.centerX(),baselineY,textPaint);
                canvas.drawRect(targetRect,paint);
            }
            canvas.restore();
    }
}

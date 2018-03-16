package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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

    private Paint paint,pathPaint;

    private int cellWidth;   //cell为正方形 width=height

    private int mbasepointX,mbasePointY;

    private int mSquareWidth;

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
            cellWidth=mSquareWidth/row;
        }else {
            cellWidth=mSquareWidth/column;
        }

        mbasepointX=mWidth/2-mSquareWidth/2;
        mbasePointY=mHeight/2-mSquareWidth/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(mbasepointX,mbasePointY,mbasepointX+mSquareWidth,mbasePointY+mSquareWidth,pathPaint);
        canvas.translate(mWidth/2,mHeight/2);    //让canvas原点居中
        drawOutlinePath(canvas);

    }

    private void drawOutlinePath(Canvas canvas){
        float dx,dy;
        if (row%2!=0){
            dy=(row-1)/2*cellWidth+cellWidth/2;
        }else {
            dy=row*cellWidth/2;
        }
        if (column%2!=0){
            dx=(column-1)/2*cellWidth+cellWidth/2;
        }else {
            dx=column*cellWidth/2;
        }
        path.moveTo(0-dx+cellWidth/3+1.5f,0-dy+1.5f);
        path.lineTo(0-dx+1.5f,0-dy+1.5f);
        path.lineTo(0-dx+1.5f,dy-1.5f);
        path.lineTo(0-dx+1.5f+cellWidth/3,dy-1.5f);

        path.moveTo(dx-1.5f-cellWidth/3,-dy+1.5f);
        path.lineTo(dx-1.5f,-dy+1.5f);
        path.lineTo(dx-1.5f,dy-1.5f);
        path.lineTo(dx-1.5f-cellWidth/3,dy-1.5f);
        canvas.drawPath(path,paint);
    }
}

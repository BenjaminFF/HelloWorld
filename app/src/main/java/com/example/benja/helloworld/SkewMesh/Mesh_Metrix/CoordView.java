package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by benja on 2018/3/15.
 */

public class CoordView extends View{

    private int mWidth,mHeight;
    private Point point;
    private Paint coordPaint,textPaint,pointPaint;

    private Path coordPath;
    public CoordView(Context context) {
        super(context);
    }

    public CoordView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        point=new Point(300,300);

        coordPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        coordPaint.setStyle(Paint.Style.STROKE);
        coordPaint.setColor(Color.YELLOW);
        coordPaint.setStrokeWidth(3);

        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(Color.YELLOW);

        coordPath=new Path();

        pointPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStrokeWidth(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);
        textPaint.setTextSize(mWidth/15);
        pointPaint.setTextSize(mWidth/20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/10,mWidth/10);

        drawCoord(canvas);

        canvas.drawCircle(point.x,point.y,10,pointPaint);
        canvas.drawText("A("+point.x+","+point.y+")",point.x+mWidth/30,point.y+mWidth/30,pointPaint);
    }

    private void drawCoord(Canvas canvas){
        coordPath.moveTo(0,0);
        coordPath.lineTo(0,mHeight*8/10);
        coordPath.lineTo(-mWidth/30,mHeight*8/10-mWidth/30);

        coordPath.moveTo(0,mHeight*8/10);
        coordPath.lineTo(mWidth/30,mHeight*8/10-mWidth/30);

        coordPath.moveTo(0,0);
        coordPath.lineTo(mWidth*8/10,0);
        coordPath.lineTo(mWidth*8/10-mWidth/30,mWidth/30);

        coordPath.moveTo(mWidth*8/10,0);
        coordPath.lineTo(mWidth*8/10-mWidth/30,-mWidth/30);
        canvas.drawPath(coordPath,coordPaint);
        canvas.drawText("O",-mWidth/15,0,textPaint);
        canvas.drawText("x",mWidth*8/10-mWidth/30,mWidth/30+mWidth/15,textPaint);
        canvas.drawText("y",mWidth/30+mWidth/15,mHeight*8/10,textPaint);
    }

    public void setPoint(int x,int y) {
        point.set(x,y);
    }
}

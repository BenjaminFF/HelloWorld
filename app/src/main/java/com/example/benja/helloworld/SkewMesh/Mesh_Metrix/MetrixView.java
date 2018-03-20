package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.benja.helloworld.BottomBar.MiscUtils;
import com.example.benja.helloworld.R;

/**
 * Created by benja on 2018/3/15.
 */

public class MetrixView extends View {
    private int row,column;

    private MyMatrix myMatrix;

    private Path outPath;

    private int mWidth,mHeight;

    private Paint paint,pathPaint,textPaint;

    private float cellWidth;   //cell为正方形 width=height

    private Paint.FontMetrics fontMetrics;

    private RectF targetRect;

    private int baselineY;   //画数字的基线Y坐标

    public MetrixView(Context context) {
        super(context);
    }

    public MetrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.MetrixView,0,0);
        row=a.getInteger(R.styleable.MetrixView_row,-1);
        column=a.getInteger(R.styleable.MetrixView_column,-1);
        cellWidth=a.getDimension(R.styleable.MetrixView_cellWidth, MiscUtils.dpToPixel(context,12));

        myMatrix=new MyMatrix(row,column);

        paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        outPath=new Path();

        pathPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setColor(Color.GREEN);
        pathPaint.setStrokeWidth(3);

        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        targetRect=new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);

        textPaint.setTextSize(cellWidth*6/10);
        textPaint.setLetterSpacing(-0.1f);
        fontMetrics=textPaint.getFontMetrics();
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawOutlinePath(canvas);
        drawMetrixText(canvas);
    }

    private void drawOutlinePath(Canvas canvas){
        canvas.save();
        canvas.translate(mWidth/2,mHeight/2);
        float startX=-cellWidth*column/2+cellWidth/4;
        float startY=-cellWidth*row/2-cellWidth/4;
        outPath.moveTo(startX,startY);
        outPath.lineTo(startX-cellWidth/2,startY);
        outPath.lineTo(startX-cellWidth/2,startY+cellWidth/2+cellWidth*row);
        outPath.lineTo(startX,startY+cellWidth/2+cellWidth*row);
        outPath.moveTo(-startX,-startY);
        outPath.lineTo(-startX+cellWidth/2,-startY);
        outPath.lineTo(-startX+cellWidth/2,-startY-cellWidth/2-cellWidth*row);
        outPath.lineTo(-startX,-startY-cellWidth/2-cellWidth*row);
        canvas.drawPath(outPath,paint);
        canvas.restore();
    }

    private void drawMetrixText(Canvas canvas){

        canvas.save();
        int dx=(int)(mWidth/2-(cellWidth*column)/2);
        int dy=(int)(mHeight/2-(cellWidth*row)/2);
        canvas.translate(dx,dy);
        for (int i=0;i<row;i++)
            for(int j=0;j<column;j++){
                targetRect.left=cellWidth*j;
                targetRect.right=cellWidth*(j+1);
                targetRect.top=cellWidth*i;
                targetRect.bottom=cellWidth*(i+1);

                //(top+bottom)/2-bottom
                baselineY=(int)(targetRect.centerY()-(fontMetrics.top+fontMetrics.bottom)/2);
                canvas.drawText(myMatrix.matrix[i][j]+"",targetRect.centerX(),baselineY,textPaint);
                //canvas.drawRect(targetRect,paint);
            }
            canvas.restore();
    }

    public void setMatrixbyTranslate(int x,int y){
        myMatrix.matrix[0][2]=x;
        myMatrix.matrix[1][2]=y;
        invalidate();
    }
}

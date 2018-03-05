package com.example.benja.helloworld.PageTurn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by benja on 2018/3/5.
 */

public class PageTurnView extends View{

    private Point a,f,g,e,h,c,j,b,k,i,d;

    private float mWidth,mHeight;

    private Path pathA;

    private Paint paintA;

    private void CalPointByLowRightA(Point a) {         //右下角翻页计算
        f.x = mWidth;
        f.y = mHeight;

        g.x = (a.x + f.x) / 2;
        g.y = (a.y + f.y) / 2;

        float mf = f.x - g.x;        //m为g在ef上的垂线
        float gm = f.y - g.y;

        e.x = g.x - gm * gm / mf;      //em*mf=gm^2
        e.y = f.y;

        float em = g.x - e.x;
        float ef = f.x - e.x;

        h.x = f.x;
        h.y = f.y - ef *gm/em;       //hf/gm=ef/em

        float hf = f.y - h.y;

        //令n点为ag中点，这样有三角形cjf和ehf得,cjf/ehf=3/2; ef/cf=2/3
        //float cf=f.x-c.x;
        c.x = f.x - 3 * ef / 2;
        c.y = f.y;

        //同理，jf/hf=3/2;
        //float jf = f.y - j.y;
        j.x=f.x;
        j.y = f.y - 3 * hf / 2;

        b.x = (a.x + e.x) / 2;
        b.y = (a.y + e.y) / 2;    //b为ae中点

        k.x = (a.x + h.x) / 2;
        k.y = (a.y + h.y) / 2;   //k为ah中点

        d.x = (c.x + 2 * e.x + b.x) / 4;    //bec做贝塞尔曲线，e为控制点，d为贝塞尔曲线的中点
        d.y = (2 * e.y + c.y + b.y) / 4;

        i.x = (j.x + 2 * h.x + k.x) / 4;
        i.y = (2 * h.y + j.y + k.y) / 4;   //jhk做贝塞尔曲线，h为控制点，i为贝塞尔曲线的中点
    }

    private Path getPathAFromLowRight(){
        pathA.reset();
        pathA.lineTo(0, mHeight);//移动到左下角
        pathA.lineTo(c.x,c.y);//移动到c点
        pathA.quadTo(e.x,e.y,b.x,b.y);//从c到b画贝塞尔曲线，控制点为e
        pathA.lineTo(a.x,a.y);//移动到a点
        pathA.lineTo(k.x,k.y);//移动到k点
        pathA.quadTo(h.x,h.y,j.x,j.y);//从k到j画贝塞尔曲线，控制点为h
        pathA.lineTo(mWidth,0);//移动到右上角
        pathA.close();//闭合区域
        return pathA;
    }

    private void initView(){
        a=new Point();
        f=new Point();
        g=new Point();
        e=new Point();
        h=new Point();
        c=new Point();
        j=new Point();
        b=new Point();
        k=new Point();
        i=new Point();
        d=new Point();

        pathA=new Path();
        paintA=new Paint();
        paintA.setColor(Color.BLACK);
        paintA.setStyle(Paint.Style.STROKE);
    }

    public PageTurnView(Context context) {
        super(context);
        initView();
    }

    public PageTurnView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
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

        a.x=mWidth/2-300;
        a.y=mHeight/2+300;
        CalPointByLowRightA(a);
        pathA = getPathAFromLowRight();
        canvas.drawPath(pathA,paintA);
        canvas.drawLine(d.x,d.y,i.x,i.y,paintA);
    }
}

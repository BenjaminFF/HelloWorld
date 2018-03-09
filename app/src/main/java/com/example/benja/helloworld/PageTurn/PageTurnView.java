package com.example.benja.helloworld.PageTurn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by benja on 2018/3/5.
 */

public class PageTurnView extends View{

    private Point a,f,g,e,h,c,j,b,k,i,d;

    private int mWidth,mHeight;

    private Bitmap bitmapA;
    private Bitmap bitmapB;
    private Bitmap bitmapC;

    private Paint mPaintA;
    private Paint mPaintB;
    private Paint mPaintC;

    private Path pathA;
    private Path pathC;

    private float curX,curY,lastX,lastY;

    private int touchSlop;

    private boolean turnState;

    private enum PageDapArea{           //手指没有滑动只是点击时的Area
        LEFT,MENU,LOWRIGHT,TOPRIGHT,MIDDLERIGHT
    }

    private enum PageMoveArea{         //手指满足滑动时的Area
        TOP,MIDDLE,DOWN
    }
    private PageDapArea dapArea;

    private PageMoveArea moveArea;

    public PageTurnView(Context context) {
        super(context);
        initView(context);
    }

    public PageTurnView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);

        this.bitmapA = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.RGB_565);
        this.bitmapB = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.RGB_565);
        this.bitmapC = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.RGB_565);
        drawContentInBitmapA();
        drawContentInBitmapB();
        drawContentInBitmapC();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.setDrawFilter(new PaintFlagsDrawFilter(0,Paint.FILTER_BITMAP_FLAG|Paint.ANTI_ALIAS_FLAG));
        drawContentA(canvas);
        drawContentC(canvas);
        drawContentB(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX=event.getX();
                lastY=event.getY();
                setArea(lastX,lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                curX=event.getX();
                curY=event.getY();
                if (canPageTurn(curX,curY,lastX,lastY)){

                }
            break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
        }
        return true;
    }

    private void initView(Context context){
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
        pathC=new Path();

        mPaintA=new Paint();
        mPaintA.setStyle(Paint.Style.FILL);
        mPaintA.setColor(Color.GREEN);

        mPaintB=new Paint();
        mPaintB.setStyle(Paint.Style.FILL);
        mPaintB.setColor(Color.YELLOW);

        mPaintC=new Paint();
        mPaintC.setStyle(Paint.Style.FILL);
        mPaintC.setColor(Color.LTGRAY);

        touchSlop= ViewConfiguration.get(context).getScaledTouchSlop();

        turnState=false;
    }
    
    private void setArea(float x,float y){
        if (x>0&&x<mWidth/3){
            pageArea=PageArea.LEFT;
        }else if (x>=mWidth/3&&x<=mWidth*2/3){
            pageArea=PageArea.MENU;
        }else {
            if (y>mHeight/2){
                pageArea=PageArea.LOWRIGHT;
            }else {
                pageArea=PageArea.TOPRIGHT;
            }
        }
    }

    private void calcPoints(Point a,Point f) {         //右下角翻页计算

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

    /*计算c坐标来判断它的坐标是否小于0，小于0就重新计算a，因为书籍的左边是装订的*/
    private float calcPointCX(float touchX,float touchY,Point f){

        g.x = (touchX + f.x) / 2;
        g.y = (touchY + f.y) / 2;

        e.x = g.x - (f.y - g.y) * (f.y - g.y) / (f.x - g.x);
        e.y = f.y;

        return e.x - (f.x - e.x) / 2;
    }

    private void setTouchPoint(float x,float y){
        if (isTurning()){              //如果正在翻页就不用判断了
            switch (pageArea){
                case LEFT:
                    break;
                case MENU:
                    break;
            }
        }
        if(calcPointCX(x,y,f)>0){
            a.x=x;
            a.y=y;
            calcPoints(a,f);
        }else {
            calcPoints(a,f);
        }
        invalidate();
    }
    /*如果滑动距离大于最小滑动距离，就可以翻页*/
    private boolean canPageTurn(float x1,float y1,float x2,float y2){
        int distance=(int)Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
        if (distance>=touchSlop){
            return true;
        }else {
            return false;
        }
    }

    /*来判断是否正在翻页*/
    private boolean isTurning(){
        if (turnState){
            return true;
        }else {
            return false;
        }
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


    private Path getPathDefault() {
        this.pathA.reset();
        this.pathA.lineTo(0, this.mHeight);
        this.pathA.lineTo(this.mWidth, this.mHeight);
        this.pathA.lineTo(this.mWidth, 0);
        this.pathA.close();
        return this.pathA;
    }

    private void drawContentInBitmapA(){
        Canvas canvas=new Canvas(bitmapA);
        canvas.drawPath(getPathDefault(),mPaintA);
        /*在这里写BitmapA的内容*/
    }

    private void drawContentInBitmapB(){
        Canvas canvas=new Canvas(bitmapB);
        canvas.drawPath(getPathDefault(),mPaintB);
        /*在这里写BitmapB的内容*/
    }

    private void drawContentInBitmapC(){
        Canvas canvas=new Canvas(bitmapC);
        canvas.drawPath(getPathDefault(),mPaintC);
        /*在这里写BitmapC的内容*/
    }

    private void drawContentA(Canvas canvas){  //把A区域画在屏幕上
        canvas.save();
        canvas.clipPath(pathA);
        canvas.drawBitmap(bitmapA,0,0,null);
        canvas.restore();
    }

    private void drawContentC(Canvas canvas){  //把C区域画在屏幕上
        canvas.save();
        canvas.clipPath(pathA);
        canvas.clipPath(getPathC(), Region.Op.REVERSE_DIFFERENCE);  //C区域为A区域-A区域与abdi区域的交集
        canvas.drawBitmap(bitmapC,0,0,null);
        canvas.restore();
    }

    private void drawContentB(Canvas canvas){  //把A的部分画在屏幕上
        canvas.save();
        canvas.clipPath(pathA,Region.Op.DIFFERENCE);
        canvas.clipPath(pathC, Region.Op.DIFFERENCE);
        canvas.drawBitmap(bitmapB,0,0,null);
        canvas.restore();
    }

    private Path getPathC(){
        pathC.reset();
        pathC.moveTo(this.i.x, this.i.y);
        pathC.lineTo(this.k.x, this.k.y);
        pathC.lineTo(this.a.x, this.a.y);
        pathC.lineTo(this.b.x, this.b.y);
        pathC.lineTo(this.d.x, this.d.y);
        pathC.close();
        return pathC;
    }
}

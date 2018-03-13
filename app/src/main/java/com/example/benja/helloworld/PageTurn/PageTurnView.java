package com.example.benja.helloworld.PageTurn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/**
 * Created by benja on 2018/3/5.
 */

public class PageTurnView extends View{

    private Point a,f,g,e,h,c,j,b,k,i,d,z;   //z点为A左右阴影的交点
    private int mWidth,mHeight;

    private Bitmap bitmapA;
    private Bitmap bitmapB;
    private Bitmap bitmapC;

    private Paint mPaintA;
    private Paint mPaintB;
    private Paint mPaintC;

    private Path pathA,pathC,pathleftsA,pathrightsA; //pathrightshandowA

    private float curX,curY,lastX,lastY;

    private int touchSlop;

    private boolean isMoving;

    private float dToae,iToah;

    private enum PageDapArea{           //手指没有滑动只是点击时的Area
        LEFT,MENU,LOWRIGHT,TOPRIGHT,MIDRIGHT
    }

    private enum PageMoveArea{         //手指满足滑动时的Area
        TOP,MIDDLE,LOW
    }
    private PageDapArea dapArea;

    private PageMoveArea moveArea;

    private Scroller mScroller;

    private GradientDrawable gdA1,gdA2,gdC,gdB;

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
        if (!isMoving){
            drawDefault(canvas);
        }else {
            drawContentA(canvas);
            drawContentC(canvas);
            drawContentB(canvas);
        }
        Log.i("test","onDrawed");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX=event.getX();
                lastY=event.getY();
                setDapArea(lastX,lastY);        //一开始就确定两个Area
                setMoveArea(lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                curX=event.getX();
                curY=event.getY();
                if (!isMoving){        //第一次判断手指滑动距离是否满足最小滑动距离
                    if (canPageTurn(curX,curY,lastX,lastY)){  //不满足的话就一定是
                        isMoving=true;                        //Dap操作
                        updatePoints(curX,curY);
                    }
                }else {
                    updatePoints(curX,curY);
                }

            break;
            case MotionEvent.ACTION_UP:
                cancelAnim();
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
        z=new Point();

        pathA=new Path();
        pathC=new Path();
        pathleftsA=new Path();
        pathrightsA=new Path();

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

        isMoving=false;
        moveArea=PageMoveArea.LOW;

        mScroller=new Scroller(context,new DecelerateInterpolator());

        gdA1=new GradientDrawable();
        gdA2=new GradientDrawable();
        gdB=new GradientDrawable();
        gdC=new GradientDrawable();
    }
    
    private void setDapArea(float x,float y){
        if (x>0&&x<mWidth/3){
            dapArea=PageDapArea.LEFT;
        }else if (x>=mWidth/3&&x<=mWidth*2/3){
            dapArea=PageDapArea.MENU;
        }else {
            if (y>0&&y<mHeight/3){
                dapArea=PageDapArea.TOPRIGHT;
            }else if (y>=mHeight/3&&y<=mHeight*2/3){
                dapArea=PageDapArea.MIDRIGHT;
            }else {
                dapArea=PageDapArea.LOWRIGHT;
            }
        }
    }

    private void setMoveArea(float y){
        if (y>0&&y<mHeight/3){
            moveArea=PageMoveArea.TOP;
        }else if (y>=mHeight/3&&y<=mHeight*2/3){
            moveArea=PageMoveArea.MIDDLE;
        }else {
            moveArea=PageMoveArea.LOW;
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

        //计算d点到直线ae的距离
        float lA = a.y-e.y;
        float lB = e.x-a.x;
        float lC = a.x*e.y-e.x*a.y;
        dToae = Math.abs((lA*d.x+lB*d.y+lC)/(float) Math.hypot(lA,lB));

        //计算i点到直线ah的距离
        float rA = a.y-h.y;
        float rB = h.x-a.x;
        float rC = a.x*h.y-h.x*a.y;
        iToah = Math.abs((rA*i.x+rB*i.y+rC)/(float) Math.hypot(rA,rB));

        float af=(float)Math.hypot(a.x-a.y,f.x-f.y);
        float zf=(float)Math.sqrt(iToah*iToah+dToae*dToae)+af;


        //zx/ax=zf/af
        z.x=f.x-zf/af*(f.x-a.x);
        z.y=f.y-zf/af*(f.y-a.y);
    }

    /**
     * 如果c点x坐标小于0,根据触摸点重新测量a点坐标
     */
    private void calcPointAByTouchPoint(){
        float w0 = mWidth - c.x;

        float w1 = Math.abs(f.x - a.x);
        float w2 = mWidth * w1 / w0;
        a.x = Math.abs(f.x - w2);

        float h1 = Math.abs(f.y - a.y);
        float h2 = w2 * h1 / w1;
        a.y = Math.abs(f.y - h2);
    }

    /*计算c坐标来判断它的坐标是否小于0，小于0就重新计算a，因为书籍的左边是装订的*/
    private float calcPointCX(float touchX,float touchY,Point f){

        g.x = (touchX + f.x) / 2;
        g.y = (touchY + f.y) / 2;

        e.x = g.x - (f.y - g.y) * (f.y - g.y) / (f.x - g.x);
        e.y = f.y;

        return e.x - (f.x - e.x) / 2;
    }

    private void updatePoints(float x,float y){
            switch (moveArea){
                case LOW:
                    f.x=mWidth;
                    f.y=mHeight;
                    a.x=x;
                    a.y=y;
                    calcPoints(a,f);
                    if (calcPointCX(x,y,f)<0){
                        calcPointAByTouchPoint();
                        calcPoints(a,f);
                    }
                    postInvalidate();
                    break;
                case MIDDLE:
                    f.x=mWidth;
                    f.y=mHeight;
                    a.x=x;
                    a.y=mHeight-0.5f;
                    calcPoints(a,f);
                    postInvalidate();
                    break;
                case TOP:
                    f.x=mWidth;
                    f.y=0;
                    a.x=x;
                    a.y=y;
                    calcPoints(a,f);   //必须先计算全部点来判断cx是否小于零
                    if (calcPointCX(x,y,f)<0){
                        calcPointAByTouchPoint();
                        calcPoints(a,f);
                    }
                    postInvalidate();
                    break;
            }
        Log.i("test","invalidated");
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

    private Path getPathAFromTopRight(){
        pathA.reset();
        pathA.lineTo(c.x,c.y);//移动到c点
        pathA.quadTo(e.x,e.y,b.x,b.y);//从c到b画贝塞尔曲线，控制点为e
        pathA.lineTo(a.x,a.y);//移动到a点
        pathA.lineTo(k.x,k.y);//移动到k点
        pathA.quadTo(h.x,h.y,j.x,j.y);//从k到j画贝塞尔曲线，控制点为h
        pathA.lineTo(mWidth,mHeight);//移动到右下角
        pathA.lineTo(0, mHeight);//移动到左下角
        pathA.close();
        return pathA;
    }

    private Path getPathAFromMidRight(){
        pathA.reset();
        pathA.moveTo(0,0);
        pathA.lineTo(0,mHeight);
        pathA.lineTo(a.x,mHeight);
        pathA.lineTo(a.x,0);
        pathA.close();
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
        if (moveArea==PageMoveArea.LOW||moveArea==PageMoveArea.MIDDLE){
            canvas.clipPath(getPathAFromLowRight());
        }else if (moveArea==PageMoveArea.TOP){
            canvas.clipPath(getPathAFromTopRight());
        }
        canvas.drawBitmap(bitmapA,0,0,null);
        if (moveArea==PageMoveArea.TOP||moveArea==PageMoveArea.LOW){
            drawPathALeftShandow(canvas);
            drawPathARightShandow(canvas);
        }else {
            drawPathAMidShandow(canvas);
        }
        canvas.restore();
    }

    private void drawPathALeftShandow(Canvas canvas){
        canvas.restore();
        canvas.save();

        int deepColor=0x11111111;
        int lightColor=0x00111111;

        int[] gradientColors = new int[] {deepColor,lightColor};//渐变颜色数组

        int left=(int)e.x;
        int right=(int)(e.x+dToae);
        int top;
        int bottom;

        float rotateDegrees;

        gdA1.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        gdA1.setColors(gradientColors);
        gdA1.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        if (moveArea==PageMoveArea.MIDDLE||moveArea==PageMoveArea.LOW){
            top=(int)e.y;
            bottom=(int)(e.y+mHeight*10);
            rotateDegrees=(float)(Math.toDegrees(Math.atan2(a.y-e.y,a.x-e.x))-90);

        }else {
            top=(int)(e.y-mHeight*10);
            bottom=(int)e.y;
            rotateDegrees=(float) (Math.toDegrees(Math.atan2(a.y-e.y,a.x-e.x))+90);
        }

        gdA1.setBounds(left,top,right,bottom);

        canvas.clipPath(getPathleftsA());
        canvas.rotate(rotateDegrees, e.x, e.y);//以e为中心点旋转
        gdA1.draw(canvas);
    }

    private void drawPathAMidShandow(Canvas canvas){      //从中间滑动时
        canvas.restore();
        canvas.save();

        int deepColor=0x33111111;
        int lightColor=0x00111111;

        int[] gradientColors = new int[] {deepColor,lightColor};//渐变颜色数组

        int left=(int)(a.x-20);
        int right=(int)(a.x+20);
        int top=0;
        int bottom=mHeight;

        GradientDrawable gradientDrawable=new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT,gradientColors);
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);//线性渐变

        gradientDrawable.setBounds(left,top,right,bottom);//设置阴影矩形
        gradientDrawable.draw(canvas);
    }

    private Path getPathleftsA(){
        pathleftsA.reset();
        pathleftsA.moveTo(z.x,z.y);
        pathleftsA.lineTo(d.x,d.y);
        pathleftsA.lineTo(e.x,e.y);
        pathleftsA.lineTo(a.x,a.y);
        pathleftsA.close();
        return pathleftsA;
    }

    private Path getPathrightsA(){
        pathrightsA.reset();
        pathrightsA.moveTo(z.x,z.y);
        pathrightsA.lineTo(i.x,i.y);
        pathrightsA.lineTo(h.x,h.y);
        pathrightsA.lineTo(a.x,a.y);
        pathrightsA.close();
        return pathrightsA;
    }

    private void drawPathARightShandow(Canvas canvas){
        canvas.restore();
        canvas.save();

        int deepColor=0x11111111;
        int lightColor=0x00111111;

        int[] gradientColors = new int[] {deepColor,lightColor};//渐变颜色数组

        int left=(int)h.x;
        int right=(int)h.x+mWidth*10;
        int top;
        int bottom;

        float rotateDegrees=(float)Math.toDegrees(Math.atan2(a.y-h.y,a.x-h.x));

        if (moveArea==PageMoveArea.MIDDLE||moveArea==PageMoveArea.LOW){
            gdA2.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
            gdA2.setColors(gradientColors);
            gdA2.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            top=(int)(h.y);
            bottom=(int)(h.y+iToah);
            Log.i("test1",rotateDegrees+"");
        }else {
            gdA2.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
            gdA2.setColors(gradientColors);
            gdA2.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            top=(int)(h.y-iToah);
            bottom=(int)(h.y);
        }

        gdA2.setBounds(left,top,right,bottom);

        canvas.clipPath(getPathrightsA());
        canvas.rotate(rotateDegrees, h.x, h.y);//以h为中心点旋转
        gdA2.draw(canvas);
    }

    private void drawContentC(Canvas canvas){  //把C区域画在屏幕上
        canvas.save();
        canvas.clipPath(pathA);
        canvas.clipPath(getPathC(), Region.Op.REVERSE_DIFFERENCE);  //C区域为A区域-A区域与abdi区域的交集
        canvas.drawBitmap(bitmapC,0,0,null);
        drawPathCShandow(canvas);

        canvas.restore();
    }

    private void drawPathCShandow(Canvas canvas){
        int deepColor=0x55111111;
        int lightColor=0x00111111;

        int[] gradientColors = new int[] {deepColor,lightColor};//渐变颜色数组

        float aTof =(float) Math.hypot((a.x - f.x),(a.y - f.y));//a到f的距离

        int left=(int)c.x;
        int right=(int)(c.x+aTof/4);
        int top;
        int bottom;

        float rotateDegrees;

        gdC.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
        gdC.setColors(gradientColors);
        gdC.setGradientType(GradientDrawable.LINEAR_GRADIENT);//线性渐变

        if (moveArea==PageMoveArea.MIDDLE||moveArea==PageMoveArea.LOW){
            top=(int)(c.y-mHeight*10);
            bottom=(int)(c.y);
            rotateDegrees=(float) Math.toDegrees(Math.atan2(f.x-c.x,f.y-j.y));
        }else {
            top=0;
            bottom=mHeight*10;
            rotateDegrees=-1*(float) Math.toDegrees(Math.atan2(f.x-c.x, j.y-f.y));
        }
        gdC.setBounds(left,top,right,bottom);//设置阴影矩形

        canvas.rotate(rotateDegrees, c.x, c.y);//以c为中心点旋转
        gdC.draw(canvas);
    }

    private void drawContentB(Canvas canvas){  //把A的部分画在屏幕上
        canvas.save();
        canvas.clipPath(pathA,Region.Op.DIFFERENCE);
        canvas.clipPath(pathC, Region.Op.DIFFERENCE);
        canvas.drawBitmap(bitmapB,0,0,null);
        drawPathBShandow(canvas);
        canvas.restore();
    }

    private void drawPathBShandow(Canvas canvas){
        int deepColor=0x55111111;
        int lightColor=0x00111111;

        int[] gradientColors = new int[] {deepColor,lightColor};//渐变颜色数组

        float aTof =(float) Math.hypot((a.x - f.x),(a.y - f.y));//a到f的距离

        int left=(int)c.x;
        int right=(int)(c.x+aTof/4);
        int top;
        int bottom;

        float rotateDegrees;

        gdC.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        gdC.setColors(gradientColors);

        if (moveArea==PageMoveArea.MIDDLE||moveArea==PageMoveArea.LOW){
            top=(int)(c.y-mHeight*10);
            bottom=(int)(c.y);
            rotateDegrees=(float) Math.toDegrees(Math.atan2(f.x-c.x,f.y-j.y));
        }else {
            top=0;
            bottom=mHeight*10;
            rotateDegrees=-1*(float) Math.toDegrees(Math.atan2(f.x-c.x, j.y-f.y));
        }
        gdC.setBounds(left,top,right,bottom);//设置阴影矩形

        canvas.rotate(rotateDegrees, c.x, c.y);//以c为中心点旋转
        gdC.draw(canvas);
    }

    private void drawDefault(Canvas canvas){
        getPathDefault();
        canvas.drawBitmap(bitmapA,0,0,null);
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

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){   //如果return true表示这个动画未结束
            float x=mScroller.getCurrX();
            float y=mScroller.getCurrY();

            updatePoints(x,y);
            if (mScroller.getFinalX()==x&&mScroller.getFinalY()==y){
                isMoving=false;
            }
        }
    }

    private void cancelAnim(){
        int dx,dy;
        if(moveArea==PageMoveArea.TOP){
            dx=(int)(mWidth-1-a.x);
            dy=(int)(1-a.y);
        }else {
            dx=(int)(mWidth-1-a.x);
            dy=(int)(mHeight-1-a.y);
        }

        mScroller.startScroll((int)a.x,(int)a.y,dx,dy,400);
    }
}

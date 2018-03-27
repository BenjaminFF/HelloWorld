package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;

import com.example.benja.helloworld.R;

/**
 * Created by benja on 2018/3/21.
 */

public class Rotate3dView extends ViewGroup{

    private Matrix matrix;

    private Camera camera;

    private float scale;

    private int mWidth,mHeight;

    private Scroller mScroller;



    public enum Orientation{
        Vertical(0),Horizontal(1);

        int id;

        Orientation(int id) {
            this.id=id;
        }

        public static Orientation getOrientation(int id){
            for(Orientation orientation:values()){
                if (orientation.id==id) return orientation;
            }
            throw new IllegalArgumentException();
        }
    }

    private Orientation mOrientation;

    private boolean mLoopable;

    private int leftBorder,rightBorder;

    private float curX,curY,lastX,lastY;

    private int mTouchSlop;

    private int childIndex;

    private boolean isScrolling;

    private float mDegree;

    public Rotate3dView(Context context) {
        super(context);
        initRotate3dView(context);
    }

    public Rotate3dView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initRotate3dView(context);

        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.Rotate3dView,0,0);
        mOrientation=Orientation.getOrientation(a.getInt(R.styleable.Rotate3dView_orientation,0));
        mLoopable=a.getBoolean(R.styleable.Rotate3dView_Loopable,false);
    }

    private void initRotate3dView(Context context){
        camera = new Camera();
        matrix = new Matrix();
        mScroller=new Scroller(context,new AccelerateInterpolator());
        mOrientation=Orientation.Horizontal;
        mLoopable=false;

        scale = context.getResources().getDisplayMetrics().density;

        mTouchSlop= ViewConfiguration.get(context).getScaledTouchSlop();

        childIndex=0;

        isScrolling=false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);

        for(int i=0;i<getChildCount();i++){
            View childView = getChildAt(i);

            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }

        curX=mWidth;
        curY=mHeight;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount=getChildCount();
        if (mLoopable){

        }else {
            if (mOrientation==Orientation.Horizontal){
                leftBorder=0;
                for(int i=0;i<childCount;i++){
                    View childView=getChildAt(i);
                    childView.layout(i*childView.getMeasuredWidth(),0,(i+1)*childView.getMeasuredWidth(),childView.getMeasuredHeight());
                    if (i==childCount-1){
                        rightBorder=i*childView.getMeasuredWidth();
                    }
                }
            }else {
                leftBorder=0;
                rightBorder=0;
                for(int i=0;i<childCount;i++){
                    View childView=getChildAt(i);
                    childView.layout(0,i*childView.getMeasuredHeight(),childView.getMeasuredWidth(),(i+1)*childView.getMeasuredHeight());
                    if(i==childCount-1){
                        rightBorder=i*childView.getMeasuredHeight();
                    }
                }
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX=ev.getX();
                lastY=ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                curX=ev.getX();
                curY=ev.getY();
                boolean intercepted=(mOrientation==Orientation.Horizontal&&Math.abs(lastX-curX)>mTouchSlop)||
                        (mOrientation==Orientation.Vertical&&Math.abs(lastY-curY)>mTouchSlop);
                if (intercepted){
                    isScrolling=true;
                    return true;
                }
                lastX=curX;
                lastY=curY;
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        getParent().requestDisallowInterceptTouchEvent(true);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX=event.getX();
                lastY=event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                curX=event.getX();
                curY=event.getY();
                if (!mLoopable){
                    if (mOrientation==Orientation.Horizontal){
                        float dx=lastX-curX;  //右滑curX>lastX
                        if (dx<0&&childIndex==0){
                            scrollTo(leftBorder,0);
                            return true;
                        }else if(dx>0&&childIndex==getChildCount()-1){
                            scrollTo(rightBorder,0);
                            return true;
                        }
                        scrollBy((int)dx,0);
                        invalidate();
                    }else{
                        float dy=lastY-curY;   //下滑curY>lastY
                        if (dy<0&&childIndex==0){
                            scrollTo(0,leftBorder);
                            return true;
                        }else if (dy>0&&childIndex==getChildCount()-1){
                            scrollTo(0,rightBorder);
                            return true;
                        }
                        scrollBy(0,(int)dy);
                    }
                }
                lastX=curX;
                lastY=curY;
                break;
            case MotionEvent.ACTION_UP:
                int targetIndex;           //左边的childview占父view一半以上targetIndex就是它,要向右滑，dx为负
                if (mOrientation==Orientation.Horizontal){
                    targetIndex=(getScrollX()+getWidth()/2)/getWidth();
                    int dx=targetIndex*getWidth()-getScrollX();
                    mScroller.startScroll(getScrollX(),0,dx,0);
                    invalidate();
                }else {
                    targetIndex=(getScrollY()+getHeight()/2)/getHeight();
                    int dy=targetIndex*getHeight()-getScrollY();
                    mScroller.startScroll(0,getScrollY(),0,dy);
                    invalidate();
                }
               break;
        }

        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            if (mScroller.getCurrX()==mScroller.getFinalX()&&mScroller.getCurrY()==mScroller.getFinalY()){
                if (mOrientation==Orientation.Horizontal){
                    childIndex=(getScrollX()+5)/getWidth();
                }else {
                    childIndex=(getScrollY()+5)/getHeight();
                }
                isScrolling=false;
                curX=mWidth;
                curY=mHeight;
            }
           invalidate();//一定要加上，不然可能这个方法不会执行，具体原因还不清楚
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        canvas.save();

        mDegree=60;

        camera.save();
        camera.setLocation(0,0,8);
        camera.rotateY(mDegree);
        camera.getMatrix(matrix);
        camera.restore();

        // 修正失真，主要修改 MPERSP_0 和 MPERSP_1
        float[] mValues = new float[9];
        matrix.getValues(mValues);			    //获取数值
        mValues[6] = mValues[6]/scale;			//数值修正
        mValues[7] = mValues[7]/scale;			//数值修正
        matrix.setValues(mValues);			    //重新赋值

        // 调节中心点
        matrix.preTranslate(-mWidth/2, -mHeight/2);
        matrix.postTranslate(mWidth/2, mHeight/2);
        Log.i("aTest",mDegree+"");

        canvas.concat(matrix);
        drawChild(canvas,getChildAt(childIndex),getDrawingTime());

        matrix.reset();
        canvas.restore();
        drawChild(canvas,getChildAt(childIndex+1),getDrawingTime());
    }
}

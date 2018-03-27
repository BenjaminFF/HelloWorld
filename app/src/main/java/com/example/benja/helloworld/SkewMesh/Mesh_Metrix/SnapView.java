package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.Toast;

import com.example.benja.helloworld.R;

/**
 * Created by benja on 2018/3/15.
 */

public class SnapView extends ViewGroup{

    private Scroller mScroller;

    private int Orientation;  //0表示Vertical 1表示Horizontal

    private int Direction;           //0表示向左或向上，1表示向右或向下

    private int leftBorder,rightBorder;

    private int childindex=0;

    private boolean isScrolling=false;

    private SnapViewListener snapViewListener;

    public SnapView(Context context) {
        super(context);
    }

    public SnapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.SnapView,0,0);
        Orientation=a.getInteger(R.styleable.SnapView_Orientation,0);
        a.recycle();
        mScroller=new Scroller(context,new LinearInterpolator());
        Direction=1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 为ScrollerLayout中的每一个子控件测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
            int childCount=getChildCount();
            if (Orientation==0){
                for(int i=0;i<childCount;i++){
                    View childView=getChildAt(i);
                    childView.layout(0,i*childView.getMeasuredHeight(),childView.getMeasuredWidth(),(i+1)*childView.getMeasuredHeight());
                }
            }else {
                for(int i=0;i<childCount;i++){
                    View childView=getChildAt(i);
                    childView.layout(i*childView.getMeasuredWidth(),0,(i+1)*childView.getMeasuredWidth(),childView.getMeasuredHeight());
                }
            }

            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(childCount - 1).getRight();

    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
            if (mScroller.getFinalX()==mScroller.getCurrX()&&mScroller.getFinalY()==mScroller.getCurrY()){
                isScrolling=false;
            }
        }
    }

    public void StartScroll(){
        if (Direction==1){
            View childview=getChildAt(childindex);
            if (Orientation==1){
                mScroller.startScroll(childview.getLeft(),0,childview.getMeasuredWidth(),0,800);
            }else {
                mScroller.startScroll(0,childview.getTop(),0,childview.getMeasuredHeight(),800);
            }
            childindex++;
            invalidate();
        }else{
            View childview=getChildAt(childindex);
            if (Orientation==1){
                mScroller.startScroll(childview.getLeft(),0,-childview.getMeasuredWidth(),0,800);
            }else {
                mScroller.startScroll(0,childview.getTop(),0,-childview.getMeasuredHeight(),800);
            }
            childindex--;
            invalidate();
        }
        isScrolling=true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                if (childindex==0){
                    Direction=1;        //向左移动，childindex++
                }else if (childindex==getChildCount()-1){
                    Direction=0;
                }
                if (!isScrolling) {
                    StartScroll();
                    if (snapViewListener!=null){
                        snapViewListener.OnSnapViewClickListener(childindex);
                    }
                }
                return true;
                default:break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    public void setSnapViewListener(SnapViewListener snapViewListener){
        this.snapViewListener=snapViewListener;
    }

    public int getChildindex() {
        return childindex;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}

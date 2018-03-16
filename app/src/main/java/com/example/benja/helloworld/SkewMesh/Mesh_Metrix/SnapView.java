package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
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

    private int Direction;           //0表示向左，1表示向右

    private int leftBorder,rightBorder;

    private int childindex=0;

    public SnapView(Context context) {
        super(context);
    }

    public SnapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.SnapView,0,0);
        Orientation=a.getInteger(R.styleable.SnapView_Orientation,0);
        mScroller=new Scroller(context,new DecelerateInterpolator());
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
        }
    }

    public void StartNegativeScroll(){   //向左或向上
        if (childindex<getChildCount()-1){
            View childview=getChildAt(childindex);
            if (Orientation==1){
                mScroller.startScroll(childview.getLeft(),0,childview.getMeasuredWidth(),0,1000);
            }else {
                mScroller.startScroll(0,childview.getTop(),0,childview.getMeasuredHeight(),1000);
            }
            invalidate();
            childindex++;
        }else {
            Toast.makeText(getContext(),"No more",Toast.LENGTH_SHORT).show();
        }
    }

    public void StartPositiveScroll(){   //向右或向下
        if (childindex>0){
            View childview=getChildAt(childindex);
            if (Orientation==1){
                mScroller.startScroll(childview.getLeft(),0,-childview.getMeasuredWidth(),0,1000);
            }else {
                mScroller.startScroll(0,childview.getTop(),0,-childview.getMeasuredHeight(),1000);
            }
            invalidate();
            childindex--;
        }else {
            Toast.makeText(getContext(),"No more",Toast.LENGTH_SHORT).show();
        }
    }
}

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
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

    public Rotate3dView(Context context) {
        super(context);
        initRotate3dView(context);
    }

    public Rotate3dView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initRotate3dView(context);

        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.Rotate3dView,0,0);
        mOrientation=Orientation.getOrientation(a.getInt(R.styleable.Rotate3dView_Orientation,0));
        mLoopable=a.getBoolean(R.styleable.Rotate3dView_Loopable,false);
    }

    private void initRotate3dView(Context context){
        camera = new Camera();
        matrix = new Matrix();
        mScroller=new Scroller(context,new LinearInterpolator());
        mOrientation=Orientation.Horizontal;
        mLoopable=false;

        scale = context.getResources().getDisplayMetrics().density;
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
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount=getChildCount();
        if (mLoopable){

        }else {
            if (mOrientation==Orientation.Horizontal){
                leftBorder=0;
                rightBorder=0;
                for(int i=0;i<childCount;i++){
                    View childView=getChildAt(i);
                    childView.layout(i*childView.getMeasuredWidth(),0,(i+1)*childView.getMeasuredWidth(),childView.getMeasuredHeight());
                    rightBorder+=(i+1)*childView.getMeasuredWidth();
                }
            }else {
                leftBorder=0;
                rightBorder=0;
                for(int i=0;i<childCount;i++){
                    View childView=getChildAt(i);
                    childView.layout(0,i*childView.getMeasuredHeight(),childView.getMeasuredWidth(),(i+1)*childView.getMeasuredHeight());
                    rightBorder+=(i+1)*childView.getMeasuredHeight();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}

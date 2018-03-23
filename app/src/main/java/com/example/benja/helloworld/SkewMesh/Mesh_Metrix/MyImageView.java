package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.benja.helloworld.R;

/**
 * Created by benja on 2018/3/23.
 */

public class MyImageView extends View {

    private ScaleType mScaleType;

    private Matrix mDrawableMatrix;

    private Drawable mDrawable;

    private Matrix mMatrix;

    private int mDrawableWidth;
    private int mDrawableHeight;

    private enum ScaleType{
        CENTER (0),CENTER_CROP(1),CENTER_INSIDE(2);
        int id;
        ScaleType(int id){
            this.id=id;
        }

        static ScaleType getScaleType(int id){
            for(ScaleType scaleType:values()){
                if (scaleType.id==id) return scaleType;
            }
            throw  new IllegalArgumentException();
        }
    }

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mMatrix=new Matrix();

        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyImageView,0,0);
        mScaleType=ScaleType.getScaleType(a.getInt(R.styleable.MyImageView_ScaleType,0));
        mDrawable=a.getDrawable(R.styleable.MyImageView_src);

        if (mDrawable != null) {
            setImageDrawable(mDrawable);
        }

        a.recycle();
    }

    private void configBounds(){

        int vWidth=getWidth();
        int vHeight=getHeight();

        int dWidth=mDrawableWidth;
        int dHeight=mDrawableHeight;

        mDrawable.setBounds(0,0,dWidth,dHeight);

        if((vWidth==dWidth)&&(vHeight==dHeight)){
            mDrawableMatrix=mMatrix;
            return;
        }

        float dx,dy,scale;

        switch (mScaleType){
            case CENTER:
                mDrawableMatrix=mMatrix;
                dx=(vWidth-dWidth)*0.5f;
                dy=(vHeight-dHeight)*0.5f;
                mDrawableMatrix.setTranslate(dx,dy);
                break;
            case CENTER_CROP:
                mDrawableMatrix=mMatrix;
                if (dWidth*vHeight>vWidth*dHeight){
                    scale=(float)vHeight/(float)dHeight;
                    dx=(vWidth-dWidth*scale)*0.5f;
                    dy=0;
                    Log.i("Test","dy=0");
                }else{
                    scale=(float)vWidth/(float)dWidth;
                    dy=(vHeight-dHeight*scale)*0.5f;
                    dx=0;
                }
                mDrawableMatrix.setScale(scale,scale);
                mDrawableMatrix.postTranslate(dx,dy);
                break;
            case CENTER_INSIDE:
                mDrawableMatrix=mMatrix;
                if (dWidth<vWidth&&dHeight<vHeight){
                    scale=1;
                }else {
                    scale = Math.min((float) vWidth / (float) dWidth,
                            (float) vHeight / (float) dHeight);
                }
                dx = Math.round((vWidth - dWidth * scale) * 0.5f);
                dy = Math.round((vHeight - dHeight * scale) * 0.5f);
                mDrawableMatrix.setScale(scale,scale);
                mDrawableMatrix.postTranslate(dx,dy);
                break;
            default:break;
        }
    }

    public void setImageDrawable(Drawable drawable){
            mDrawableWidth=drawable.getIntrinsicWidth();
            mDrawableHeight=drawable.getIntrinsicHeight();

            if (mDrawable!=drawable){
                invalidate();
            }

        mDrawable=drawable;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        configBounds();

        canvas.save();
        canvas.concat(mDrawableMatrix);
        mDrawable.draw(canvas);
        canvas.restore();
    }
}

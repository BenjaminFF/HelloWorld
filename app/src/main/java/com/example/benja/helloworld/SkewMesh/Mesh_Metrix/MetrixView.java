package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by benja on 2018/3/15.
 */

public class MetrixView extends View {
    private int row,column;

    private int rowindex[],columnindex[];

    private int mWidth,mHeight;
    public MetrixView(Context context) {
        super(context);
    }

    public MetrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
    }
}

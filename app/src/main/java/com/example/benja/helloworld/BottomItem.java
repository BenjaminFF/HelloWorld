package com.example.benja.helloworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by benja on 2018/3/1.
 */

public class BottomItem extends View {

    private Drawable un_icon,icon;

    private RectF bounds;

    private float mSize;
    public BottomItem(Context context) {
        super(context);
    }

    public BottomItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bounds=new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        bounds.left=width/2-mSize/2;
        bounds.right=width/2+mSize/2;
        bounds.top=height/2-mSize/2;
        bounds.bottom=height/2+mSize/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}

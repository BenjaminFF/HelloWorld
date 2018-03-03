package com.example.benja.helloworld.BottomBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.benja.helloworld.R;

/**
 * Created by benja on 2018/3/1.
 */

public class BottomItem extends View {

    private Drawable un_icon,icon;

    private Rect bounds;

    private float mSize;

    private String Title;

    private int mId;

    private boolean State=false;

    private int active_color,unactive_color;

    public BottomItem(Context context) {
        super(context);
        bounds=new Rect();
    }

    public BottomItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bounds=new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        bounds.left=(int)(width/2-mSize/2);
        bounds.right=(int)(width/2+mSize/2);
        bounds.top=(int)(height/2-mSize/2);
        bounds.bottom=(int)(height/2+mSize/2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (State){
            icon.setColorFilter(getResources().getColor(active_color,null), PorterDuff.Mode.SRC_ATOP);
            icon.setBounds(bounds);
            icon.draw(canvas);
        }else {
            un_icon.setColorFilter(getResources().getColor(unactive_color,null), PorterDuff.Mode.SRC_ATOP);
            un_icon.setBounds(bounds);
            un_icon.draw(canvas);
        }
    }

    public void setUn_icon(int resId) {
        un_icon=getResources().getDrawable(resId,null);
    }

    public void setIcon(int resId) {
        icon=getResources().getDrawable(resId,null);
    }

    public void setSelected(boolean state) {
        State = state;
        invalidate();
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setmSize(float mSize) {
        this.mSize = mSize;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public int getId(){
        return mId;
    }

    public boolean isSelected(){
        return State;
    }

    public void setActive_color(int active_color) {
        this.active_color = active_color;
    }

    public void setUnactive_color(int unactive_color) {
        this.unactive_color = unactive_color;
    }
}

package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.benja.helloworld.BottomBar.MiscUtils;
import com.example.benja.helloworld.R;

/**
 * Created by benja on 2018/3/18.
 */

public class MyTextView extends View {

    private Paint textpaint;

    private float textstrokewidth;

    private int textColor;

    private float textSize;

    private String text;

    private int mWidth,mHeight;

    private Paint.FontMetrics fontMetrics;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyTextView,0,0);
        textstrokewidth=a.getFloat(R.styleable.MyTextView_textStrokeWidth,1.0f);
        textColor=a.getColor(R.styleable.MyTextView_textColor,getResources().getColor(R.color.colorAccent,null));
        textSize=a.getDimension(R.styleable.MyTextView_textSize, MiscUtils.dpToPixel(context,20.0f));

        text=a.getString(R.styleable.MyTextView_text);

        textpaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        textpaint.setTextAlign(Paint.Align.CENTER);
        textpaint.setTextSize(textSize);
        textpaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textpaint.setTypeface(Typeface.SERIF);
        textpaint.setStrokeWidth(textstrokewidth);
        textpaint.setColor(textColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);

        fontMetrics=textpaint.getFontMetrics();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float baseLineY=mHeight/2-(fontMetrics.top+fontMetrics.bottom)/2;
        canvas.drawText(text,mWidth/2,baseLineY,textpaint);
    }
}

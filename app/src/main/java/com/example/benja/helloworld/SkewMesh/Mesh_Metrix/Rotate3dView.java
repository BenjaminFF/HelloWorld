package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

import android.content.Context;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.Scroller;

import com.example.benja.helloworld.R;

/**
 * Created by benja on 2018/3/21.
 */

public class Rotate3dView extends ImageView{


    private float degree;
    private Matrix matrix;
    private Paint paint;
    private Camera camera;

    private int mWidth,mHeight;
    private Bitmap bitmap;

    private Scroller mScroller;
    public Rotate3dView(Context context) {
        super(context);
    }

    public Rotate3dView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        camera = new Camera();
        matrix = new Matrix();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.android_camera2);
        this.setScaleType(ScaleType.CENTER_INSIDE);
        this.setImageBitmap(bitmap);
        mScroller=new Scroller(context);
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

        //setImageDrawable();

        /*matrix.reset();
        camera.save();
        camera.translate(0,0,10);
        camera.rotateY(30);
        camera.getMatrix(matrix);
        camera.restore();

        // 修正失真，主要修改 MPERSP_0 和 MPERSP_1
        float[] mValues = new float[9];
        matrix.getValues(mValues);			    //获取数值
        mValues[6] = mValues[6]/5;			//数值修正
        mValues[7]=mValues[7]/10;
        matrix.setValues(mValues);			    //重新赋值

        matrix.preTranslate(-mWidth/2,-mHeight/2);
        matrix.postTranslate(mWidth/2,mHeight/2);*/

        /*canvas.drawBitmap(bitmap,matrix,null);*/
    }
}

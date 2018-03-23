package com.example.benja.helloworld.SkewMesh.Mesh_Metrix;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.benja.helloworld.R;

/**
 * Created by benja on 2018/3/23.
 */

public class MyImageView extends View {

    public enum ScaleType{
        CENTER (0),CENTER_CROP(1),CENTER_INSIDE(2);
        int id;
        ScaleType(int id){
            this.id=id;
        }

        static ScaleType getScaleType(int id){
            for(ScaleType scaleType:values()){
                if (scaleType.id==id) return scaleType;
            }
            return new ;
        }
    }

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyImageView,0,0);
        ScaleType scaleType=new ScaleType()

        a.recycle();
    }
}

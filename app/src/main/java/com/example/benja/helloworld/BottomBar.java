package com.example.benja.helloworld;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by benja on 2018/3/1.
 */

public class BottomBar extends LinearLayout {

    private int items_xml;
    public BottomBar(Context context) {
        super(context);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void InitItems(Context context, @Nullable AttributeSet attrs){
        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.BottomBar);
        items_xml=a.getInt(R.styleable.BottomBar_items_xml,-1);
        a.recycle();

        XmlResourceParser xmlParser = getResources().getXml(items_xml);


    }
}

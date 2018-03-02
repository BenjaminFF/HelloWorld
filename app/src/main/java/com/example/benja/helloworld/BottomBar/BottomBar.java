package com.example.benja.helloworld.BottomBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.benja.helloworld.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by benja on 2018/3/1.
 */

public class BottomBar extends LinearLayout {

    private int items_xml;

    private Float icon_size,middle_icon_size;

    private ArrayList<BottomItem> items;

    private BottomItem item=null;
    public BottomBar(Context context) {
        super(context);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        InitItems(context,attrs);
    }


    private void InitItems(Context context, @Nullable AttributeSet attrs){
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.BottomBar,0,0);
        items_xml=a.getResourceId(R.styleable.BottomBar_items_xml,0);
        icon_size=a.getDimension(R.styleable.BottomBar_icon_size,MiscUtils.dpToPixel(context,30));
        middle_icon_size=a.getDimension(R.styleable.BottomBar_middle_icon_size,MiscUtils.dpToPixel(context,30));
        a.recycle();

        XmlResourceParser parser=getResources().getXml(items_xml);
        try {
            int event=parser.getEventType();
            while (event!=XmlResourceParser.END_DOCUMENT){
                switch (event){
                    case XmlPullParser.START_DOCUMENT:
                        items=new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("tab".equals(parser.getName())){
                            BottomItem bottomItem=new BottomItem(context);
                            bottomItem.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
                            bottomItem.setUn_icon(parser.getAttributeResourceValue(null,"un_icon",0));
                            bottomItem.setIcon(parser.getAttributeResourceValue(null,"icon",0));
                            bottomItem.setTitle(parser.getAttributeValue(null,"title"));
                            items.add(bottomItem);
                        }
                        break;
                    default:break;
                }
                event=parser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.i("test",items.size()+"");
        this.setWeightSum(items.size());
        removeAllViews();
        setOrientation(LinearLayout.HORIZONTAL);
        for(int i=0;i<items.size();i++){
            if (i==items.size()/2){
                items.get(i).setmSize(middle_icon_size);
            }else {
                items.get(i).setmSize(icon_size);
            }
            addView(items.get(i));
        }
        invalidate();
    }
}

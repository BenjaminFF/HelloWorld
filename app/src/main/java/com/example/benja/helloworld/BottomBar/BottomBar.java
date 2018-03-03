package com.example.benja.helloworld.BottomBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.benja.helloworld.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by benja on 2018/3/1.
 */

public class BottomBar extends LinearLayout{

    private int items_xml;

    private Float icon_size,middle_icon_size;

    private int active_color,unactive_color;

    private ArrayList<BottomItem> items;

    private BottomItem item=null;

    private OnTabSelectedListener tabSelectedListener;

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
        active_color=a.getResourceId(R.styleable.BottomBar_bb_active_color,-1);
        unactive_color=a.getResourceId(R.styleable.BottomBar_bb_unactive_color,-1);
        Log.i("test",active_color+"");
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
                            bottomItem.setActive_color(active_color);
                            bottomItem.setUnactive_color(unactive_color);
                            bottomItem.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
                            bottomItem.setUn_icon(parser.getAttributeResourceValue(null,"un_icon",0));
                            bottomItem.setIcon(parser.getAttributeResourceValue(null,"icon",0));
                            bottomItem.setTitle(parser.getAttributeValue(null,"title"));
                            bottomItem.setId(parser.getIdAttributeResourceValue(-1));
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

        for(int i=0;i<items.size();i++){
            final BottomItem item=items.get(i);
           item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.isSelected()){
                        return;
                    }
                    for(int j=0;j<items.size();j++){
                        items.get(j).setSelected(false);
                    }
                    item.setSelected(true);
                    if (tabSelectedListener!=null){
                        tabSelectedListener.onTabSelected(item.getId());
                    }
                }
            });
        }
        items.get(0).setSelected(true);
    }

    public void setTabSelectedListener(OnTabSelectedListener listener){
        tabSelectedListener=listener;
    }
}

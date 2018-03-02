package com.example.benja.helloworld;

import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.benja.helloworld.BottomBar.BottomBar;
import com.example.benja.helloworld.BottomBar.OnTabSelectedListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static String TAG="test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomBar bottomBar=findViewById(R.id.BottomBar);
        bottomBar.setTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(int TabId) {
                switch (TabId){
                    case R.id.tab_all:
                        Log.i("test","tab_all");
                        break;
                    case R.id.tab_idea:
                        Log.i("test","tab_idea");
                        break;
                    case R.id.tab_star:
                        Log.i("test","tab_star");
                        break;
                }
            }
        });
    }
}

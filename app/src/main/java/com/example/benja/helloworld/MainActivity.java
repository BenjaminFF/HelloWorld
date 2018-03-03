package com.example.benja.helloworld;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.FrameLayout;

import com.example.benja.helloworld.BottomBar.BottomBar;
import com.example.benja.helloworld.BottomBar.OnTabSelectedListener;


public class MainActivity extends AppCompatActivity {
    private static String TAG="test";
    BottomBar bottomBar;
    Fragment appFragment,ideaFragment,starFragment;
    FragmentManager fragmentManager;
    FrameLayout main_frame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomBar=findViewById(R.id.BottomBar);
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

        main_frame=findViewById(R.id.main_frame);

    }
}

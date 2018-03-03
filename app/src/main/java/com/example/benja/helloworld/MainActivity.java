package com.example.benja.helloworld;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.FrameLayout;

import com.example.benja.helloworld.BottomBar.BottomBar;
import com.example.benja.helloworld.BottomBar.OnTabSelectedListener;
import com.example.benja.helloworld.MainFragment.AppFragment;
import com.example.benja.helloworld.MainFragment.IdeaFragment;
import com.example.benja.helloworld.MainFragment.StarFragment;


public class MainActivity extends AppCompatActivity{
    private static String TAG="test";
    BottomBar bottomBar;
    Fragment appFragment,ideaFragment,starFragment,curFragment;
    FragmentManager fragmentManager;
    FrameLayout main_frame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitFragment();

        bottomBar=findViewById(R.id.BottomBar);
        bottomBar.setTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(int TabId) {
                TabSelected(TabId);
            }
        });


    }

    private void TabSelected(int TabId){
        switch (TabId){
            case R.id.tab_all:
                switchFragment(appFragment).commit();
                break;
            case R.id.tab_idea:
                switchFragment(ideaFragment).commit();
                break;
            case R.id.tab_star:
                switchFragment(starFragment).commit();
                break;
        }
    }

    private void InitFragment(){
        main_frame=findViewById(R.id.main_frame);
        appFragment=new AppFragment();
        ideaFragment=new IdeaFragment();
        starFragment=new StarFragment();
        curFragment=starFragment;
        fragmentManager=getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.main_frame,curFragment,curFragment.getClass().getName()).commit();

    }

    private FragmentTransaction switchFragment(Fragment targetFragment){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        if (!targetFragment.isAdded()){
            transaction.hide(curFragment);
            transaction.add(R.id.main_frame,targetFragment,targetFragment.getClass().getName());
        }else {
            transaction.hide(curFragment).show(targetFragment);
        }
        curFragment=targetFragment;
        return transaction;
    }
}
